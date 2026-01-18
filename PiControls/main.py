import network
import time
from umqtt.simple import MQTTClient
from machine import Pin, PWM
import ujson

from servo import Servo

yaw_servo = Servo(PWM(Pin("GP0")))  # Servo pin is connected to GP0
pitch_servo = Servo(PWM(Pin("GP1")))
trigger_servo = Servo(PWM(Pin("GP2")))


def servo_Map(x, in_min, in_max, out_min, out_max):
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min


def servo_Angle_Yaw(angle):
    if angle < 0:
        angle = 0
    if angle > 180:
        angle = 180
    yaw_servo.goto(round(servo_Map(angle, 0, 180, 0, 1024)))  # Convert range value to angle value


def servo_Angle_Pitch(angle):
    if angle < 0:
        angle = 0
    if angle > 180:
        angle = 180
    pitch_servo.goto(round(servo_Map(angle, 0, 180, 0, 1024)))


def trigger_Init(angle):
    trigger_servo.goto(round(servo_Map(80, 0, 180, 0, 1024)))
    time.sleep(0.5)
    trigger_servo.goto(round(servo_Map(angle, 0, 180, 0, 1024)))


# Initializing default position
yaw_angle = 90
pitch_angle = 90
trigger_angle = 180
servo_Angle_Yaw(yaw_angle)
servo_Angle_Pitch(pitch_angle)
time.wait(0.5)
trigger_Init(trigger_angle)


def servo_Shoot():
    trigger_servo.goto(round(servo_Map(30, 0, 180, 0, 1024)))
    time.sleep(1)
    trigger_servo.goto(round(servo_Map(180, 0, 180, 0, 1024)))

# WiFi credentials
ssid = 'Tenda'  # 'DIGI_ffa8e4'
password = 'walkback669'

# MQTT server details
mqtt_server = 'frontier.go.ro'
mqtt_port = 1883
mqtt_control = 'control/topic'
mqtt_shoot = 'shooting/topic'


# Connect to WiFi
def connect_wifi(ssid, password):
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    wlan.connect(ssid, password)

    while not wlan.isconnected():
        print('Connecting to network...')
        time.sleep(1)

    print('Network connected:', wlan.ifconfig())


# MQTT message callback
def mqtt_callback(topic, msg):
    print((topic, msg))
    if mqtt_control in topic:
        json_payload = ujson.loads(msg)
        print(type(json_payload))
        x = json_payload["x"]
        y = json_payload["y"]
        print("x pos: ", x, " ", "y pos: ", y)
        servo_Angle_Yaw(x)
        servo_Angle_Pitch(y)
    elif mqtt_shoot in topic:
        servo_Shoot()


# Main program
def main():
    connect_wifi(ssid, password)

    client = MQTTClient('pico_client', mqtt_server, port=mqtt_port)
    client.set_callback(mqtt_callback)
    client.connect()
    print('Connected to MQTT broker')

    client.subscribe(mqtt_control)
    print('Subscribed to topic:', mqtt_control)

    client.subscribe(mqtt_shoot)
    print('Subscribed to topic:', mqtt_shoot)

    while True:
        client.wait_msg()

    client.disconnect()


# Run the main program
main()

