import random
import csv

MAX_NUM = 25
MIN_NUM = 1

SIZE_KECIL = 10
SIZE_SEDANG = 40
SIZE_BESAR = 80

def generate_set_partition_dataset(n):
    # Generate a list of n positive integers
    numbers = [random.randint(MIN_NUM, MAX_NUM) for _ in range(n)]

    # Calculate the total sum of the numbers
    total_sum = sum(numbers)

    # Check if the total sum is even, as set partition requires equal sums
    if total_sum % 2 != 0:
        # Adjust the last number to make the total sum even
        numbers[-1] += 1

    # Shuffle the numbers to create randomness
    random.shuffle(numbers)

    print(numbers)

    return numbers

if __name__ == "__main__":
    # Generate randomized order dataset
    with open('data_kecil.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_set_partition_dataset(SIZE_KECIL))

    with open('data_sedang.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_set_partition_dataset(SIZE_SEDANG))

    with open('data_besar.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_set_partition_dataset(SIZE_BESAR))
