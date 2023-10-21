# main.py
# Run the program as a whole
from web_scrap import scrap_job_postings

def main():
    """Run the program as a whole."""
    
    # Grab job postings data
    job_postings = scrap_job_postings()

if __name__ == "__main__":
    main()