#!/usr/bin/env bash
# Run static analysis and tests before pushing (see githooks/pre-push).
# Usage:
#   ./scripts/check-before-push.sh
#   RUN_CONNECTED_TESTS=1 ./scripts/check-before-push.sh   # also runs instrumented tests (device/emulator)
# Skip (emergency only):
#   SKIP_PUSH_CHECKS=1 git push ...
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

if [[ "${SKIP_PUSH_CHECKS:-}" == "1" ]]; then
  echo "[check-before-push] SKIP_PUSH_CHECKS=1 — skipping checks."
  exit 0
fi

echo "[check-before-push] Detekt..."
./gradlew --no-daemon detekt

echo "[check-before-push] Android Lint..."
./gradlew --no-daemon lint

echo "[check-before-push] Unit tests (all modules, includes :architecture-test / Konsist)..."
./gradlew --no-daemon testDebugUnitTest

if [[ "${RUN_CONNECTED_TESTS:-}" == "1" ]]; then
  echo "[check-before-push] Instrumented tests (requires device or emulator)..."
  ./gradlew --no-daemon connectedDebugAndroidTest
else
  echo "[check-before-push] Skipping connectedDebugAndroidTest (set RUN_CONNECTED_TESTS=1 to enable)."
fi

echo "[check-before-push] Done."
