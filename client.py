import json, socket
# It sends a location request and displays the weather response.
# User must match HOST and port and the server must be running.
HOST = "127.0.0.1"
PORT = 5050

def main():
    print("User connected. Type a location like 'denver, co' or 'miami, fl'. Type 'quit' to exit.")
   
   # Get user input, default to Celsius in case "enter" is pressed
    unit = input("Unit (C/F) [C]: ").strip().upper() or "C"

   # Connect to the server
    with socket.create_connection((HOST, PORT)) as sock:
        f = sock.makefile("rwb")
        
        while True:
            loc = input("\nLocation: ").strip()
            if loc.lower() in ("quit", "exit"):
                break

            req = {"location": loc, "unit": unit}
            f.write((json.dumps(req) + "\n").encode("utf-8"))
            f.flush()

            resp_line = f.readline()
            if not resp_line:
                print("Server disconnected.")
                break
            
            #If the server sends a valid response, display the weather information
            # If it sends an error response, display the error message
            resp = json.loads(resp_line.decode("utf-8"))
            if resp.get("ok"):
                print(f"{resp['location'].title()}: {resp['condition']}, {resp['temp']}°{resp['unit']}")
            else:
                print("Error:", resp.get("error", "Unknown error"))

if __name__ == "__main__":
    main()