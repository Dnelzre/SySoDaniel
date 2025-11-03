import os
import re
import argparse
from concurrent.futures import ThreadPoolExecutor, as_completed

def count_lines(file_path):
    """Zählt die Zeilen in einer Datei."""
    try:
        with open(file_path, 'r', encoding='utf-8', errors='ignore') as file:
            return sum(1 for _ in file)
    except Exception as e:
        print(f"Fehler beim Lesen von {file_path}: {e}")
        return 0

def find_matching_files(folder_path, regex_str):
    """Findet alle Dateien, die auf den Regex passen."""
    regex = re.compile(regex_str)
    matching_files = []
    for root, _, files in os.walk(folder_path):
        for file_name in files:
            if regex.match(file_name):
                matching_files.append(os.path.join(root, file_name))
    return matching_files

def count_lines_in_all_files_parallel(folder_path, regex_str, max_threads=8):
    """Zählt Zeilen in allen passenden Dateien parallel mit Threads."""
    matching_files = find_matching_files(folder_path, regex_str)
    total_lines = 0

    with ThreadPoolExecutor(max_workers=max_threads) as executor:
        futures = {executor.submit(count_lines, f): f for f in matching_files}
        for future in as_completed(futures):
            total_lines += future.result()

    return total_lines

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Zählt Zeilen in allen Dateien, die auf einen Regex passen (parallel).')
    parser.add_argument('folder_path', help='Pfad zum Ordner')
    parser.add_argument('regex_str', help='Regex für Dateinamen')
    parser.add_argument('--threads', type=int, default=8, help='Anzahl der Threads (Standard: 8)')

    args = parser.parse_args()

    total_lines = count_lines_in_all_files_parallel(args.folder_path, args.regex_str, args.threads)
    print(f"Gesamtanzahl der Zeilen: {total_lines}")
