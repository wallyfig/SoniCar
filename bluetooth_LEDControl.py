#Sample code that enables an Android phone with blueterm to control an LED using Bluetooth
import bluetooth
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

GPIO_LED = 18

GPIO.setup(GPIO_LED,GPIO.OUT)

def Blink(numTimes,speed):
    for i in range(0,numTimes):
        GPIO.output(GPIO_LED,True)
        print(';Blinking ' + str(i+1))
        time.sleep(speed)

        GPIO.output(GPIO_LED,False)
        print('Done Blinking LED')
        time.sleep(speed)

#Establishes Bluetooth conenction
server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_sock.bind(('',port))
server_sock.listen(1)

client_sock,address = server_sock.accept()
print('Accepted connection from ',address)

#Waitng for user input, loops afterwards
while True:
    data = client_sock.recv(1024)
    print('received [%s]' % data)

    if (data == '1'):
       print ('LED ON')
       GPIO.output(GPIO_LED,GPIO.HIGH)

    if (data == '0'):
       print ('LED OFF')
       GPIO.output(GPIO_LED,GPIO.LOW)

    if (data == '5'):
       print ('LED Blink')
       Blink(10,0.1)

    if (data == 'e'):
        print ('Exit')
        break

client_sock.close()
server_sock.close()
