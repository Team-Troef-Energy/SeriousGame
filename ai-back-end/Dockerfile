# We cannot use Alpine because Torch requires glibc; Alpine uses musl instead.
# Python 3.12 must be used because PyO3 does not support newer versions.
FROM python:3-bookworm

# Create a new user (app) with a home directory (/app), so that pip can work properly.
# We'll install the app to that directory as well.
RUN useradd -u 1000 -U -m -d /app app
USER 1000:1000
WORKDIR /app

# Copy requirements.txt and install first.
# This way, a code change will not invalidate the cache until the next step.
# Installing requirements takes a long time, and this allows it to be cached independently.
COPY requirements.txt .
RUN pip install -r requirements.txt --extra-index-url https://download.pytorch.org/whl/cpu
RUN rm -rf /app/.cache

# Disables Flask's debug mode
ENV FLASK_ENV=production
# Ensures output is not buffered, which is useful for logs
ENV PYTHONUNBUFFERED=1
ENV PYTHONPATH=/app/src
ENV PATH=$PATH:/app/.local/bin

# Copy the rest.
COPY . .

CMD ["gunicorn", "-w", "4", "-b", "0.0.0.0:5000", "src.app:app"]
