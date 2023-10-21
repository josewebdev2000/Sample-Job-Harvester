# main.py
# Run the program as a whole
from web_scrap import scrap_job_postings
from db import create_db_connection, disconnect_from_db, create_table, drop_table, insert_data_into_table, truncate_table

def main():
    """Run the program as a whole."""
    
    # Grab job postings data
    job_postings = scrap_job_postings()
    
    # Check we got the job postings
    if job_postings:
        
        # Generate a DB connection
        db_conn = create_db_connection()
        
        # Give name to table that will hold job postings
        job_postings_table_name = "job_postings"
        
        # Define the column names for the table
        job_postings_table_column_names = ("role", "company", "location", "date")
        
        # Write the schema of the table in the database
        job_postings_table_schema = "id SERIAL PRIMARY KEY NOT NULL, role VARCHAR(255) NOT NULL, company VARCHAR(255) NOT NULL, location VARCHAR(255), date DATE"
        
        # Go on and try to create a new table table
        # First try to drop the table and if an error is raised then create the table
        try:
            drop_table(db_conn, job_postings_table_name)
            
        except Exception:
            create_table(db_conn, job_postings_table_name, job_postings_table_schema)
        
        # Go on and insert the job postings
        # Track if table could be truncated with success
        could_truncate = False
        # Remember to truncate the table first to avoid generating duplicates
        try:
            truncate_table(db_conn, job_postings_table_name)
        
        except Exception as e:
            print(e)
        
        else:
            # By this point, the truncate table operation was successful
            could_truncate = True
        
        # Insert data into the database if the table could be truncated
        if could_truncate:
            
            # Convert the dicts contained in job_postings to tuples to be inserted into the db
            job_postings_as_tuples = [(str(job_posting["role"]), str(job_posting["company"]), str(job_posting["location"]), str(job_posting["date"])) for job_posting in job_postings]
            
            # Insert the data into the DB
            try:
                insert_data_into_table(db_conn, job_postings_table_name, job_postings_as_tuples, job_postings_table_column_names)
            
            except Exception as e:
                print(e)
        
        # Disconnect from the DB
        disconnect_from_db(db_conn)

if __name__ == "__main__":
    main()