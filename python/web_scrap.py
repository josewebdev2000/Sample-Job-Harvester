# web_scrap.py
# Web Scrap Data from Sample Job Postings
import requests
from bs4 import BeautifulSoup as BS
from constants import URL, PARSER

"""
Must return a list that will contain dicts of the following format:
{
    "role": "Professional role asked for by the company",
    "company": "Hiring Company",
    "location": "Location of the Company",
    "date": "Date Posted"
}
"""

def scrap_job_postings():
    """Scrap given URL to return job postings."""
    
    # List of job postings
    job_postings = []
    
    # Read HTML data from sample job postings URL
    response = requests.get(URL)
    
    # Instantiate a new web scrapping object
    scrapper = BS(response.text, PARSER)
    
    # Grab all divs with class "card-content"
    job_posting_divs = scrapper.select(".card-content")
    
    # Loop through each job posting div
    for job_posting_div in job_posting_divs:
        
        # Grab the role being asked for in the job posting
        role = job_posting_div.select("h2.title.is-5")[0].text.strip()
        
        # Grab the company that requests this role
        company = job_posting_div.select("h3.subtitle.is-6.company")[0].text.strip()
        
        # Grab the location of the company
        location = job_posting_div.select("p.location")[0].text.strip()
        
        # Grab the date of this posting
        date = job_posting_div.select("time")[0]["datetime"]
        
        # Append a new job posting to the job postings list
        job_postings.append({
            "role": role,
            "company": company,
            "location": location,
            "date": date
        })
    
    return job_postings  

if __name__ == "__main__":
    print("This Python script contains code that does web scrapping.")
    print("Do not run it directly")