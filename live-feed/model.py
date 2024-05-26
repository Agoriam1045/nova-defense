import rel
import cv2
import threading
import websocket
from cvzone.PoseModule import PoseDetector

ws_host = "ws://127.0.0.1:1045/nova"

cap = cv2.VideoCapture('video.mp4')
detector = PoseDetector()

img_format = '.png'
compression_params = [cv2.IMWRITE_AVIF_SPEED, 9]

def on_message(ws, message):
    print(message)
    pass

def on_error(ws, error):
    if cap.isOpened():
        cap.release()
        cv2.destroyAllWindows()

    print(error)

def on_close(ws, close_status_code, close_msg):
    if cap.isOpened():
        cap.release()
        cv2.destroyAllWindows()
    
    print("### closed ###")

def run_feed(ws):
    while cap.isOpened():
        success, frame = cap.read()
        if not success:
            continue

        frame = detector.findPose(frame)
        lmList, bboxInfo = detector.findPosition(frame)

        retval, buffer = cv2.imencode(img_format, cv2.resize(frame, (640, 360)), compression_params)
        cv2.imshow("image", frame);

        if cv2.waitKey(1) == ord('q'):
            break

        try:
            ws.send(buffer.tobytes(), websocket.ABNF.OPCODE_BINARY)
        except Exception as error:
            print(error)

def on_open(ws):
    threading.Thread(target=run_feed, args=(ws,)).start()
    

if __name__ == "__main__":
    ws = websocket.WebSocketApp(ws_host,
                              on_open=on_open,
                              on_message=on_message,
                              on_error=on_error,
                              on_close=on_close)

    ws.run_forever(dispatcher=rel, reconnect=5)  # Set dispatcher to automatic reconnection, 5 second reconnect delay if connection closed unexpectedly
    rel.signal(2, rel.abort)  # Keyboard Interrupt
    rel.dispatch()