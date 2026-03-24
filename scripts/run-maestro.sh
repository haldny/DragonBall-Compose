#!/usr/bin/env bash
# Run Maestro flows in .maestro/flows (install Maestro CLI first — see .maestro/README.md).
# Exports ANDROID_SERIAL so dadb/Maestro use a single device (reduces TcpForwarder timeouts).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

if ! command -v maestro >/dev/null 2>&1; then
  echo "Maestro CLI not found. Install: curl -Ls \"https://get.maestro.mobile.dev\" | bash" >&2
  exit 1
fi

adb start-server
adb wait-for-device

DEVICE="${ANDROID_SERIAL:-$(adb devices | awk '/\tdevice$/{print $1; exit}')}"
if [[ -z "${DEVICE:-}" ]]; then
  echo "No device in 'device' state. Start an AVD or connect a device." >&2
  exit 1
fi

echo "Maestro target device: $DEVICE (export ANDROID_SERIAL to pick another when several are connected)"

echo "Waiting for sys.boot_completed=1 on ${DEVICE}..."
for _ in $(seq 1 90); do
  if [[ "$(adb -s "$DEVICE" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" == "1" ]]; then
    echo "Boot completed."
    break
  fi
  sleep 2
done

# Brief settle time so adb port forwarding is stable before Maestro opens TCP forwarders.
sleep 2

# Maestro/dadb often forward on localhost:7001; another listener (e.g. AirServer) causes TcpForwarder timeouts — see .maestro/README.md and maestro#1125.
if command -v lsof >/dev/null 2>&1; then
  if lsof -nP -iTCP:7001 -sTCP:LISTEN 2>/dev/null | grep -q .; then
    echo "WARNING: Something is already listening on TCP port 7001. Maestro may fail with TcpForwarder TimeoutException until that process is stopped." >&2
    lsof -nP -iTCP:7001 -sTCP:LISTEN >&2 || true
  fi
fi

export ANDROID_SERIAL="$DEVICE"

exec maestro test "${ROOT}/.maestro/flows"
