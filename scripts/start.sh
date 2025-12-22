#!/usr/bin/env bash
set -euo pipefail

APP_USER="bookapp"
APP_GROUP="bookapp"
APP_DIR="/opt/book_repo"
DEST_JAR="${APP_DIR}/app.jar"

echo "[start] locating built jar..."
# Prefer the non-plain boot jar
JAR_CANDIDATE="$(ls -1t ${APP_DIR}/build/libs/*SNAPSHOT.jar 2>/dev/null | head -n 1 || true)"
if [[ -z "${JAR_CANDIDATE}" ]]; then
  # fallback: any jar excluding "plain"
  JAR_CANDIDATE="$(ls -1t ${APP_DIR}/build/libs/*.jar 2>/dev/null | grep -v 'plain\.jar' | head -n 1 || true)"
fi

if [[ -z "${JAR_CANDIDATE}" ]]; then
  echo "[start] ERROR: no deployable jar found under ${APP_DIR}/build/libs" >&2
  ls -la "${APP_DIR}" || true
  ls -la "${APP_DIR}/build/libs" || true
  exit 1
fi

echo "[start] using: ${JAR_CANDIDATE}"
cp -f "${JAR_CANDIDATE}" "${DEST_JAR}"
chown "${APP_USER}:${APP_GROUP}" "${DEST_JAR}"
chmod 0644 "${DEST_JAR}"

echo "[start] restarting service..."
systemctl restart bookapp.service
systemctl --no-pager -l status bookapp.service
