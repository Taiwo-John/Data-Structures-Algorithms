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
