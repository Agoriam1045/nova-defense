import cv2
from cvzone.PoseModule import PoseDetector
from websocket import create_connection
import base64
import zlib

cap = cv2.VideoCapture(0)
ws = create_connection("ws://192.168.0.194:1045/app")

detector = PoseDetector()

img_format = '.png'
compression_params = [cv2.IMWRITE_PNG_COMPRESSION, 9]

while cap.isOpened():
    success, frame = cap.read()
    if not success:
        continue

    frame = detector.findPose(frame)
    lmList, bboxInfo = detector.findPosition(frame)

    downsized_img = cv2.resize(frame, (64 * 2, 36 * 2))
    retval, buffer = cv2.imencode(img_format, downsized_img, compression_params)
    jpg_text = base64.b64encode(buffer)

    try:
        ws.send(zlib.compress(jpg_text))
    except Exception as error:
        ws = create_connection("ws://192.168.0.194:1045/app")
        print(error)

    cv2.imshow("Image", cv2.resize(downsized_img, (1280, 720)))

    if cv2.waitKey(1) & 0xFF == ord('q'): 
        break
    
cap.release()
cv2.destroyAllWindows()