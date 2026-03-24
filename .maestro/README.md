# Maestro UI flows

[Maestro](https://maestro.mobile.dev/) drives the installed app on a connected emulator or device.

## Install CLI

```bash
curl -Ls "https://get.maestro.mobile.dev" | bash
```

## Run the app

```bash
./gradlew :app:installDebug
```

## Run flows

From the repository root:

```bash
./scripts/run-maestro.sh
```

Or:

```bash
maestro test .maestro/flows
```

Single flow:

```bash
maestro test .maestro/flows/character_detail_planet.yaml
```

## Locale

Flows match **English** strings in `core/design/src/main/res/values/strings.xml` (e.g. toolbar **Characters**, detail **Detail**, tabs **Character** / **Planet**, label **Destroyed**).  
If the device uses Portuguese or Spanish, either switch the system/app locale to English for Maestro runs or duplicate flows with translated `visible` / `tapOn` text under e.g. `.maestro/flows/pt/`.

## CI

Use the same `maestro test` command after starting an emulator (e.g. GitHub Actions with `reactivecircus/android-emulator-runner`).

## Troubleshooting

### `java.util.concurrent.TimeoutException` at `TcpForwarder` / `dadb`

Maestro talks to the device over **ADB TCP port forwarding**. The same stack trace often appears when **another process is already bound to port 7001** (Maestro’s local forwarder). Common culprits: **AirServer**, screen-mirroring apps, or anything that listens on `7001`. See [mobile-dev-inc/maestro#1125](https://github.com/mobile-dev-inc/maestro/issues/1125).

Try in order:

1. **Free port 7001** — on macOS/Linux:
   ```bash
   lsof -nP -iTCP:7001 -sTCP:LISTEN
   ```
   Quit the listed app (or reconfigure it) so nothing listens on `7001`, then run `./scripts/run-maestro.sh` again. The script also prints a warning if something is listening on `7001`.
2. **One device only** — `adb devices` should show a single line in `device` state (not `offline`). Disconnect extra devices or set:
   ```bash
   export ANDROID_SERIAL=emulator-5554   # your id from adb devices
   ./scripts/run-maestro.sh
   ```
3. **Restart ADB** — `adb kill-server && adb start-server`, then run the script again.
4. **Cold-boot the AVD** — quit the emulator and start it again; wait until the home screen is up (the script already waits for `sys.boot_completed`).
5. **Close Android Studio** (or anything that holds ADB) temporarily, then retry.
6. **Upgrade Maestro** — `curl -Ls "https://get.maestro.mobile.dev" | bash` again.

`./scripts/run-maestro.sh` waits for boot completion and sets `ANDROID_SERIAL` automatically; use that instead of calling `maestro test` directly if you were hitting this error.
