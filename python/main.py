import time
from datetime import datetime
import requests
import firebase_admin
from firebase_admin import credentials, firestore, db


def getData(data):
    success = False

    while success == False:
        try:
            response = requests.get("http://192.168.2.3/status.html", auth=("admin", "admin"), timeout=3)
            found = 0
            for line in response.text.split('\r\n'):
                if line.startswith('var webdata_now_p'):
                    data['power'] = line.split('"')[1]
                    found += 1
                if line.startswith('var webdata_today_e'):
                    data['todayEnergy'] = line.split('"')[1]
                    found += 1
                if line.startswith('var webdata_total_e'):
                    data['totalEnergy'] = line.split('"')[1]
                    found += 1
            if found == 3:
                success = True
        except:
            time.sleep(2)
    return success


inverterData = {}
inverterRet = getData(inverterData)


cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)
db = firestore.client()
now = datetime.now().strftime("%m.%d.%Y %H:%M")
dateStr = now.decode('utf-8')
print(now)

data = {
    u'datetime': dateStr,
    u'power': inverterData["power"],
    u'todayEnergy': inverterData["todayEnergy"],
    u'totalEnergy': inverterData["totalEnergy"],
}

if inverterRet:
    db.collection(u'data').add(data)
    print("data updated")