import sqlite3
from flask import Flask, request, render_template

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


# CWE-79: XSS — name comes directly from HTTP request parameter into template with | safe
@app.route("/patient-profile")
def patient_profile():
    # Vulnerable: user-controlled input flows directly into template rendered with | safe
    name = request.args.get("name", "")
    return render_template("patient_detail.html", patient_name=name)


if __name__ == "__main__":
    app.run()
