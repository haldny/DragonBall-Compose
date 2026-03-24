#!/usr/bin/env bash
# Point this repository at the versioned hooks in /githooks (Git 2.9+).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

git config core.hooksPath githooks

chmod +x githooks/pre-push 2>/dev/null || true
chmod +x scripts/check-before-push.sh scripts/install-git-hooks.sh 2>/dev/null || true

echo "core.hooksPath is now: $(git config core.hooksPath)"
echo "pre-push will run scripts/check-before-push.sh when you push to main."
echo "Run ./scripts/check-before-push.sh manually anytime."
