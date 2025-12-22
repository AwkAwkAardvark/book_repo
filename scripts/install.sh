#!/usr/bin/env bash
set -euo pipefail

APP_USER="bookapp"
APP_GROUP="bookapp"
APP_HOME="/home/bookapp"
APP_DIR="/opt/book_repo"
SERVICE_FILE="/etc/systemd/system/bookapp.service"

echo "[install] creating user/home if needed..."
if ! id -u "${APP_USER}" >/dev/null 2>&1; then
  # -m creates home, -d sets it explicitly, -s nologin prevents interactive login
  useradd -r -m -d "${APP_HOME}" -s /sbin/nologin "${APP_USER}"
fi

mkdir -p "${APP_HOME}"
chown -R "${APP_USER}:${APP_GROUP}" "${APP_HOME}"

echo "[install] ensuring app directory exists..."
mkdir -p "${APP_DIR}"
chown -R "${APP_USER}:${APP_GROUP}" "${APP_DIR}"

echo "[install] installing Java 17 (Corretto) + devel tools..."
if command -v dnf >/dev/null 2>&1; then
  dnf -y install java-17-amazon-corretto-devel
elif command -v yum >/dev/null 2>&1; then
  yum -y install java-17-amazon-corretto-devel
else
  echo "No yum/dnf found. Cannot install Java automatically." >&2
  exit 1
fi

echo "[install] writing systemd unit..."
cat > "${SERVICE_FILE}" <<EOF
[Unit]
Description=Book Repo Spring Boot API
After=network.target

[Service]
Type=simple
User=${APP_USER}
Group=${APP_GROUP}
WorkingDirectory=${APP_DIR}
Environment=HOME=${APP_HOME}
Environment=SPRING_PROFILES_ACTIVE=default
ExecStart=/usr/bin/java -jar ${APP_DIR}/app.jar
Restart=always
RestartSec=5
SuccessExitStatus=143

# Give it a little room
LimitNOFILE=65535

[Install]
WantedBy=multi-user.target
EOF

chmod 644 "${SERVICE_FILE}"

echo "[install] systemd reload + enable..."
systemctl daemon-reload
systemctl enable bookapp.service

echo "[install] done."
