# Taiwo John
# Stock Analyzer problem solution.

from QueueArray import Queues


class StockTransaction:
    def __init__(self, quantity, price):
        self.quantity = quantity
        self.price = price


class Stocks:
    def __init__(self):
        self.stock_quantity = Queues()
        self.total_stock_quantity = 0
        self.total_gain_loss = 0

    def buy_stock(self, quantity, price):
        if price <= 0:
            raise ValueError("Price cannot be non-positive")
        elif quantity <= 0:
            raise ValueError("Quantity cannot be non-positive")
        else:
            self.stock_quantity.enqueue(StockTransaction(quantity, price))
            self.total_stock_quantity += quantity

    def sell(self,  quantity, price):
        if price <= 0:
            raise ValueError("Price cannot be non-positive")
        if quantity <= 0:
            raise ValueError("Quantity cannot be non-positive")
        if quantity > self.total_stock_quantity:
            raise ValueError("Not enough stocks to sell")
        else:
            self.total_stock_quantity -= quantity
            current_gain_loss = 0
            while quantity > 0:
                if quantity >= self.stock_quantity.peek().quantity:
                    gain_loss = (price - self.stock_quantity.peek().price) * self.stock_quantity.peek().quantity
                    self.total_gain_loss += gain_loss
                    current_gain_loss += gain_loss
                    quantity -= self.stock_quantity.peek().quantity
                    self.stock_quantity.dequeue()
                else:
                    gain_loss = (price - self.stock_quantity.peek().price) * quantity
                    self.total_gain_loss += gain_loss
                    current_gain_loss += gain_loss
                    self.stock_quantity.peek().quantity -= quantity
                    quantity = 0
        print(current_gain_loss)

    def get_profit(self):
        return self.total_gain_loss

    def get_remaining_stock(self):
        return self.total_stock_quantity

