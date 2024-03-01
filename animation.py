import json
import matplotlib.pyplot as plt
import networkx as nx

# データの読み込み
with open('src/main/java/CMorph/output/userJob_data', 'r') as user:
    user_data = json.load(user)

with open('src/main/java/CMorph/output/datacenter_data', 'r') as datacenter:
    datacenter_data = json.load(datacenter)

# dataCenterのポジション
# グラフの初期化
G = nx.Graph()

def extractPosition(data):
    position={}

    while (len(position)<10):
        for _ in data:
            position

