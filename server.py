import json, socket, threading

# This server listens for TCP connections from clients.
# It loads weather data from a local JSON file.
# When a client sends a location request, it responds with weather information.
HOST = "127.0.0.1"
PORT = 5050
DATA_FILE = "weather_data.json"

def c_to_f(c): 
    return round(c * 9/5 + 32, 1)

# This function loads weather data from a local JSON file abd returns a dictionary.
def locations():
    with open(DATA_FILE, "r", encoding="utf-8") as f:
        return json.load(f)["locations"]

LOCATIONS = locations()

#This function handles client connections. It runs in a separate thread for each client.
def handle_client(conn, addr):
    with conn:
        f = conn.makefile("rwb") 
        while True:
            line = f.readline()
            if not line:
                break

            try:
                req = json.loads(line.decode("utf-8"))
                location = (req.get("location") or "").strip().lower() #sends a location request
                unit = (req.get("unit") or "C").strip().upper()

                if not location:
                    raise ValueError("location is required")

                info = LOCATIONS.get(location)
                if not info:
                    resp = {"ok": False, "error": "Unknown location. Try: 'denver, co'."}
               #converts temperature from C to F
                else:
                    temp_c = float(info["temp_c"])
                    temp = temp_c if unit == "C" else c_to_f(temp_c)
                    resp = {
                        "ok": True,
                        "location": location,
                        "condition": info["condition"],
                        "unit": unit,
                        "temp": temp
                    }

                f.write((json.dumps(resp) + "\n").encode("utf-8"))
                f.flush()

            except Exception as e:
                f.write((json.dumps({"ok": False, "error": str(e)}) + "\n").encode("utf-8"))
                f.flush()


# This function starts the server. Listens for client connections and handles them in separate threads.
def main():
    print(f"Weather server starting on {HOST}:{PORT} ...")
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        s.bind((HOST, PORT))
        s.listen()
        while True:
            conn, addr = s.accept()
            threading.Thread(target=handle_client, args=(conn, addr), daemon=True).start()

if __name__ == "__main__":
    main()