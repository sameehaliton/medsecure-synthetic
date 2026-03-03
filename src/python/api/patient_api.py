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
        name = patient[1]
        # CWE-79: unsanitized patient name passed directly to template context
        return render_template("patient_detail.html", patient_name=name)
    return "Not found", 404


if __name__ == "__main__":
    app.run(debug=True)
