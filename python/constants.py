# constants.py
# Global constants to use throughout the project to web scrap data
import os
URL = "https://realpython.github.io/fake-jobs/"
PARSER = "html.parser"
DB_HOST = os.getenv("DB_HOST", "")
DB_NAME = os.getenv("DB_NAME", "")
DB_USER = os.getenv("DB_USER", "")
DB_PASS = os.getenv("DB_PASS", "")