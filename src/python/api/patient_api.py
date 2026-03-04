import sqlite3
from flask import Flask, request, render_template_string

app = Flask(__name__)
DB_PATH = "patients.db"


def get_db():
    return sqlite3.connect(DB_PATH)


# CWE-89: SQL injection — patient_id from query param inserted into f-string
@app.route("/patients")
def get_patient():
    patient_id = request.args.get("id", "")
    conn = get_db()
    cursor = conn.cursor()
    # Vulnerable: f-string concatenation creates injectable SQL
    cursor.execute(f"SELECT * FROM patients WHERE id = '{patient_id}'")
    patient = cursor.fetchone()
    conn.close()
    if patient:
        return "found", 200
    return "Not found", 404


# CWE-79: XSS — user input injected directly into render_template_string (known CodeQL sink)
@app.route("/patient-profile")
def patient_profile():
    name = request.args.get("name", "")
    # Vulnerable: user-controlled input embedded into template string before rendering
    template = "<html><body><h1>Patient: " + name + "</h1></body></html>"
    return render_template_string(template)


if __name__ == "__main__":
    app.run()
