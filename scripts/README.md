# Scripts

## `install-git-hooks.sh`

Run once per clone (or after pulling hook changes):

```bash
./scripts/install-git-hooks.sh
```

This sets `git config core.hooksPath githooks` so Git uses the tracked hooks in `githooks/`.

## `check-before-push.sh`

Runs:

1. **detekt** — static analysis  
2. **lint** — Android Lint  
3. **testDebugUnitTest** — unit tests in all modules (includes **:architecture-test** / Konsist rules)  

**Instrumented tests** are **not** run by default (slow; needs a device). To include them:

```bash
RUN_CONNECTED_TESTS=1 ./scripts/check-before-push.sh
```

## Bypass (emergency only)

```bash
SKIP_PUSH_CHECKS=1 git push ...
```

## `githooks/pre-push`

When you **push to `main`**, the hook runs `scripts/check-before-push.sh` without `RUN_CONNECTED_TESTS` (same as local default).

Pushes to other branches are not blocked by these checks.
