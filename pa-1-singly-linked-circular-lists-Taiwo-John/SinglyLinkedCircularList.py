# Taiwo Temidayo John
# Cohort 1
# Implementing a Singly Linked Circular List Algorithm.


# This is the class definition of the Linked List.
class SinglyLinkedCircularList:

    # This is the constructor.
    # It ensures that on instantiation of the Class, current points to the initial node.
    # If no initial node is creates on instantiation of the class, current points to None.
    def __init__(self, init_node=None):
        self.current = init_node
        if init_node is not None:
            self.current.next = self.current

    # This method is the insertion method.
    # It has a Time complexity of O(n) as well as a space complexity of O(n).
    def insert(self, data):

        # This creates a new node, with the data the node stores as its parameter.
        newest = Node(data)

        # This checks if the current is None, and then makes the newest inserted node, current.
        if self.current is None:
            self.current = newest
            self.current.next = self.current

        # If current is not None, the reference of the newest node points to current.
        # Then, the reference of the current node, points to the newest Node.
        # And the newest node is made current
        else:
            newest.next = self.current.next
            self.current.next = newest
            self.current = newest

    # This is the method used to search for an item in the list.
    # It takes the data of the item (an integer value).
    # This method has a time complexity of O(n), and a space complexity of O(n) as it loops through the list till it
    # gets the desired search result or not.
    def search(self, data):

        # Checks to see if current is None i.e if the list is empty.
        # If true, it returns None.
        if self.current is None:
            return None

        # This makes the current Node a start node, so that the list doesn't go on in cycles if the item is not found.
        # Then it checks if the current viz a viz the first Node that is searched's data matches the data input.
        # If it does, it returns the reference to the link of the Node.
        else:
            first_search_node = self.current
            if self.current.data == data:
                return self.current

        # If the first_search_node's data does not match the desired input, current is moved to the next item.
        # And then, loops through the rest of the items in the list to search for the desired input.
        # If the input is found, the reference to the link of the Node is returned, if not, None is returned.
            else:
                self.current = self.current.next
            while self.current != first_search_node:
                if self.current.data == data:
                    return self.current
                else:
                    self.current = self.current.next
            return None

    # This is the method used to delete items from the list.
    # It takes an integer parameter of the data of the Node that is to be deleted.
    # Its' time complexity is O(n) and also it's space complexity is O(n)
    def delete(self, data):

        # If current is none, i.e if the List is empty, it returns None.
        if self.current is None:
            return None
        else:
            # This makes the current Node a start node, so that the list doesn't go on in cycles if the Node to be
            # deleted is not found.
            # Then it checks if the current.next (the reference to the next node of the current Node) matches the node
            # to be deleted. If it does, it stores the reference to the Node in a variable called deleted_node.
            # It then makes the current node point to the node after the deleted node (this is the act of deletion).
            # It finally returns the variable deleted_node, which is a reference to the Node that was deleted.
            first_search_node = self.current
            if self.current.next.data == data:
                deleted_node = self.current.next
                self.current.next = self.current.next.next
                return deleted_node
            else:

                # This basically does the same act of deletion, only that it terminates at the Node before the initial
                # node and returns None if the node to be deleted is not found in the list.
                self.current = self.current.next
            while self.current != first_search_node:
                if self.current.next.data == data:
                    deleted_node = self.current.next
                    self.current.next = self.current.next.next
                    return deleted_node
                else:
                    self.current = self.current.next
            return None

    # The step method does only one simple thing:
    # It first checks if hte list is not empty and then moves the current node, to the node after the current node.
    def step(self):
        if self.current is not None:
            self.current = self.current.next

    # This method is used to print the data in all the Nodes in the list.
    def display_list(self):
        # It first checks to see if the list is not empty
        if self.current is not None:
            # It starts from the current Node, prints the data and loops through the nodes back to the node before the
            # start node, printing the data values of all the nodes.
            first_display_node = self.current

            print(self.current.data)
            self.current = self.current.next

            while self.current != first_display_node:
                print(self.current.data)
                self.current = self.current.next
        # If the list is empty, it prints None.
        else:
            print(None)

    # This function implements the Josephus problem.

    # Defining the function. The function takes in 3 parameters:
    # 1. The number of people in the cycle, 2. The number to count off to and 3. The number to start counting off from
    def josephus(self, number_of_people, count_off_number, start_count_off):

        # We create a list in which we store the people who have committed suicide, i.e the data that is deleted from
        # the linked list.
        suicide_list = []

        # We loop from 1 to the number of people that is given in the parameter, and add them into the linked list.
        # For example, if the number of people given is 5, it inserts the numbers 1 to 5 into 5 nodes in the list.
        for number in range(1, number_of_people + 1):
            self.insert(number)

        # We check if the start off number is not the current node's data. If this is true, we call the step() function
        # which essentially moves the current node to the next node. We repeat this step until we find the node from
        # which to start counting.
        while self.current.data != start_count_off:
            self.step()

        # We initialize a counter variable and set its value to the number of people.
        # we loop through it and decrement it until there is only one person(Node) left in the list.
        counter = number_of_people
        while counter > 1:

            # The next line of code checks if the linked list has only one element i.e if the reference of the current
            # Node points to itself or, if the current node is none (i.e if the list is empty) and returns None.
            # Simply, it checks to see if the number of nodes in the linked list is less than 2 and returns None.
            if self.current.next == self.current or self.current is None:
                return None
            # If the elements of the list are greater than 2, the else condition comes into play.
            else:
                # We initialize a variable count.
                count = 0
                # We loop through count and while it is less than count_off_number, we call the step() function, i.e we
                # move the current node to its next and also increment count.
                while count < count_off_number:
                    self.step()
                    count += 1
                # If count is equal to the count_off_number, we append the data of the node to be deleted into the list
                # initially created, and then we delete the node. After this, we move to the next node throught the
                # step() function.
                suicide_list.append(self.current.data)
                self.delete(self.current.data)
                self.step()
            counter -= 1
        # At the end of the iterations, we print the suicide_list containing the data of the Nodes we deleted.
        print(suicide_list)


# This is the Node class, where we define the attributes of each node that is created at the instantiation of the
# SinglyLinkedCircularList Class.
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

# References (for the Josephus algorithm implementation):
# 1. How To Use Circular Linked List To Solve The Josephus Problems - C And C++ | Dream.In.Code. 2019. How To Use
# Circular Linked List To Solve The Josephus Problems - C And C++ | Dream.In.Code. [ONLINE] Available at:
# https://www.dreamincode.net/forums/topic/21426-how-to-use-circular-linked-list-to-solve-the-josephus-problems/.
# [Accessed 27 January 2019]
# 2. Medium. 2019. Explaining the Josephus Algorithm – Robert R.F. DeFilippi – Medium. [ONLINE] Available at:
# https://medium.com/@rrfd/explaining-the-josephus-algorithm-11d0c02e7212. [Accessed 27 January 2019].
# 3. GeeksforGeeks. 2019. Josephus Circle using circular linked list - GeeksforGeeks. [ONLINE] Available at:
# https://www.geeksforgeeks.org/josephus-circle-using-circular-linked-list/. [Accessed 27 January 2019].
# 4. Josephus Problem using circular linked list . 2019. Josephus Problem using circular linked list . [ONLINE]
# Available at: http://blogtipscodes.blogspot.com/2011/11/josephus-problem-using-circular-linked.html.
# [Accessed 27 January 2019]

