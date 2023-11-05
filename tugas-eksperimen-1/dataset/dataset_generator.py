import random
import csv

MAX_NUM = 1000000 # 1 juta, sesuai dengan dataset yang digunakan pada paper
MIN_NUM = 1

SIZE_KECIL = 200
SIZE_SEDANG = 2000
SIZE_BESAR = 20000

def generate_sorted(n):
    generated = generate_random(n)
    generated.sort()

    return generated

def generate_random(n):
    generated = []

    for _ in range(n):
        generated.append(random.randint(MIN_NUM, MAX_NUM))

    return generated

def generate_reversed(n):
    return generate_sorted(n)[::-1]

if __name__ == "__main__":
    # Generate sorted dataset
    with open('sorted_kecil.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_sorted(SIZE_KECIL))
    with open('sorted_sedang.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_sorted(SIZE_SEDANG))
    with open('sorted_besar.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_sorted(SIZE_BESAR))

    # Generate randomized order dataset
    with open('random_kecil.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_random(SIZE_KECIL))
    with open('random_sedang.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_random(SIZE_SEDANG))
    with open('random_besar.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_random(SIZE_BESAR))

    # Generate reversed order dataset
    with open('reversed_kecil.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_reversed(SIZE_KECIL))
    with open('reversed_sedang.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_reversed(SIZE_SEDANG))
    with open('reversed_besar.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter='\n')
        writer.writerow(generate_reversed(SIZE_BESAR))
