# Taiwo Temidayo John
# DSA Programming Assignment 2
# Color-Coded Stack.


# First Create the Stack Class.
class ColorCodedStack:

    # The constructor initializes the following field variables in the first instance of the class.
    def __init__(self, n):
        self.list_size = n
        self.cards = [self.list_size]
        self.red_size = 0
        self.black_size = 0
        self.first_red = -1
        self.first_black = self.list_size

    # To push a red card, we check if it is not overlapping with the black card before we push it.
    # If it overlaps, we return a stack overflow error.
    def push_red(self, card):
        if self.first_red + 1 == self.first_black:
            print("Stack overflow")
        else:
            self.cards.insert(0, card)
            self.red_size += 1
            self.first_red += 1

    def pop_red(self):

        if self.redsize() == 0:
            return "Stack underflow"
        else:
            self.cards.pop(0)
            self.red_size -= 1
            self.first_red -= 1

    # The peek function returns the value at the top position of a stack, it returns the value at the top position for
    # the red and black stacks.
    def peek_red(self):
        return self.cards[0]

    # This function returns the size of the red stack.
    def redsize(self):
        return self.red_size

    def is_red_empty(self):
        return self.redsize() == 0

    def display_red_stack(self):
        for redcard in range(self.red_size):
            print(self.cards[redcard])

    def push_black(self, card):
        if self.first_red + 1 >= self.first_black:
            print("Stack overflow")
        else:
            self.cards.append(card)
            self.black_size += 1
            self.first_black -= 1

    def pop_black(self):
        if self.blacksize() == 0:
            return "Stack underflow"
        else:
            self.cards.pop()
            self.black_size -= 1
            self.first_black += 1

    # This method returns the first(top) element in the black stack.
    def peek_black(self):
        return self.cards[len(self.cards) - 1]

    # This function returns the size of the black stack.
    def blacksize(self):
        return self.black_size

    def is_black_empty(self):
        return self.blacksize() == 0

    def display_black_stack(self):
        blackcard = len(self.cards) - 1
        for count in range(self.black_size):
            print(self.cards[blackcard])
            blackcard -= 1
