# db.py
# Python code to handle database operations
import psycopg2
from constants import DB_HOST, DB_NAME, DB_USER, DB_PASS

def create_db_connection():
    """Establish a connection to a PostgreSQL database."""
    
    try:
        db_conn = psycopg2.connect(dbname = DB_NAME, host = DB_HOST, user = DB_USER, password = DB_PASS)
        return db_conn
    
    except psycopg2.Error as e:
        print(f"Failed to connect to PostgreSQL database: {e}")

def create_table(db_conn, table_name, table_schema):
    """Create a new table in a PostgreSQL database. In case it exists, throw an error."""
    
    try:
    
        # Create a cursor associated to the given database connection
        db_cursor = db_conn.cursor()
        
        # Dynamically create the table query
        create_table_sql_query = f"CREATE TABLE {table_name} ({table_schema});"
        
        # Execute the SQL query
        db_cursor.execute(create_table_sql_query)
        
        # Commit changes in the DB
        db_conn.commit()
    
    except psycopg2.Error as e:
        # Roll back DB to its previous state
        db_conn.rollback()
        raise Exception(f"Failed to create new PostgreSQL table: {e}")

def disconnect_from_db(db_conn):
    
    try:
        if db_conn:
            db_conn.close()
    
    except psycopg2.Error as e:
        print(f"Failed to disconnect from PostgreSQL database: {e}")

def drop_table(db_conn, table_name):
    """Delete a table from a PostgreSQL database."""
    
    try:
        
        # Create a cursor associated to the given database connection
        db_cursor = db_conn.cursor()
        
        # Build the SQL query to drop the given table
        drop_table_sql_query = f"DROP TABLE {table_name};"
        
        # Execute the query
        db_cursor.execute(drop_table_sql_query)
        
        # Commit changes in the DB
        db_conn.commit()
    
    except psycopg2.Error as e:
        # Roll back DB to its previous state
        db_conn.rollback()
        raise Exception(f"Failed to drop table of name {table_name}: {e}")

def insert_data_into_table(db_conn, table_name, rows_of_data, table_columns):
    """Insert the given data in the given table."""
    
    try:
        
        # Grab the number of placeholders required to insert data
        num_columns = len(table_columns)
        
        # Create a list of as many placeholders as necessary per column
        string_placeholders = ["%s" for i in range(num_columns)]
        
        # Convert the list to a string
        string_placeholders_str_form = ",".join(string_placeholders)
        
        # Instantiate a new cursor for this database connection
        db_cursor = db_conn.cursor()
        
        # Build the SQL query that will insert one row of data to the given table
        insert_data_sql_query = f"INSERT INTO {table_name} ({', '.join(table_columns)}) VALUES ({string_placeholders_str_form});"
        
        # Add as many rows of data there are
        db_cursor.executemany(insert_data_sql_query, rows_of_data)
        
        # Commit changes to DB
        db_conn.commit()
    
    except psycopg2.Error as e:
        
        # Revert DB to its previous state
        db_conn.rollback()
        
        # Raise an Exception
        raise Exception(f"Failed to insert data into table {table_name}: {e}")

def truncate_table(db_conn, table_name):
    """Delete all rows of data present in a table."""
    
    try:
        # Create a cursor associated to the given database conneciton
        db_cursor = db_conn.cursor()
        
        # Build the SQL query to truncate the given table
        truncate_table_sql_query = f"TRUNCATE TABLE {table_name} RESTART IDENTITY;"
        
        # Execute the SQL query
        db_cursor.execute(truncate_table_sql_query)
        
        # Commit the changes permanently to the DB
        db_conn.commit()
    
    except psycopg2.Error as e:
        # Roll back DB to its previous state
        db_conn.rollback()
        raise Exception(f"Failed to truncate table of name {table_name}: {e}")


if __name__ == "__main__":
    print("This Python Script contains code that handles database operations.")
    print("Do not run it directly")