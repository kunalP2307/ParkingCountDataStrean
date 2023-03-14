import gspread
import pandas as pd
from gspread_dataframe import set_with_dataframe
from datetime import date
import serial.tools.list_ports
import time

SHEET_NAME = "ParkingData"
CURRENT_DATA_TAB = "CurrentP0"
ALL_DATA_TAB = "P0"

def read_serial():
    ports = serial.tools.list_ports.comports()
    serialInst = serial.Serial()

    portVar = "/dev/ttyUSB1"

    serialInst.baudrate = 9600
    serialInst.port = portVar
    serialInst.open()

    print(serialInst)
    while True:
        if serialInst.in_waiting:
            packet = serialInst.readline()
            count = packet.decode('utf')
            today = str(date.today())
            timestamp = str(time.strftime('%H:%M:%S'))
            count = str(count)
            update_current_data([today.strip(), timestamp.strip(), count.strip()])

def get_all_data():
    gc = gspread.service_account(filename='/home/kunal/Downloads/parkingdatastream-3bd6d75a17b3.json')
    sh = gc.open(SHEET_NAME)
    worksheet = sh.worksheet(ALL_DATA_TAB)
    df = pd.DataFrame(worksheet.get_all_records())
    return df


def get_current_data():
    gc = gspread.service_account(filename='/home/kunal/Downloads/parkingdatastream-3bd6d75a17b3.json')
    sh = gc.open(SHEET_NAME)
    worksheet = sh.worksheet(CURRENT_DATA_TAB)
    current_row = worksheet.row_values(2)
    return current_row


def append_row_to_sheet(row):
    gc = gspread.service_account(filename='/home/kunal/Downloads/parkingdatastream-3bd6d75a17b3.json')
    sh = gc.open(SHEET_NAME)
    worksheet = sh.worksheet(ALL_DATA_TAB)
    worksheet.append_row(row)


def update_current_data(new_row):
    append_row_to_sheet(new_row)
    gc = gspread.service_account(filename='/home/kunal/Downloads/parkingdatastream-3bd6d75a17b3.json')
    sh = gc.open(SHEET_NAME)
    data = [new_row]
    #data = [[new_row[0], new_row[1], new_row[2]]]
    df = pd.DataFrame(data, columns=["Date", "Time","Count"])
    worksheet = sh.worksheet(CURRENT_DATA_TAB)
    set_with_dataframe(worksheet, df)
    print('done writing', new_row)


read_serial()


