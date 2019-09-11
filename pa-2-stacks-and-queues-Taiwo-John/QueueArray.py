# Taiwo John
# Queue Implementation.
# I implemented this class so I could use it for the Stock Analyzer problem.


class Queues:
    def __init__(self):
        self.items = []

    def is_empty(self):
        return self.items == []

    def enqueue(self, item):
        self.items.insert(0, item)

    def dequeue(self):
        try:
            self.items.pop()
        except:
            print("Queue underflow")

    def peek(self):
        return self.items[0]

    def size(self):
        return len(self.items)

    def display(self):
        if self.is_empty():
            print("Empty Queue: No item to display")
        for item in range(len(self.items)):
            print(self.items[item])
