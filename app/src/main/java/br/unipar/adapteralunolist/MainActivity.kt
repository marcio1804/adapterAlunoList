package br.unipar.adapteralunolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var nomeEstudante: EditText
    private lateinit var areaEstudante: EditText
    private lateinit var dateDisplay: TextView
    private lateinit var countDisplay: TextView
    private lateinit var estudanteListView: ListView
    private var students: ArrayList<Student> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nomeEstudante = findViewById(R.id.nomeEstudante)
        areaEstudante = findViewById(R.id.areaEstudante)
        dateDisplay = findViewById(R.id.dateDisplay)
        countDisplay = findViewById(R.id.countDisplay)
        estudanteListView = findViewById(R.id.estudanteListView)
        val addStudentButton = findViewById<Button>(R.id.Inserir)
        val resetButton = findViewById<Button>(R.id.resetButton)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        estudanteListView.adapter = adapter

        addStudentButton.setOnClickListener {
            val name = nomeEstudante.text.toString().trim()
            val area = areaEstudante.text.toString().trim()
            if (name.isNotEmpty() && area.isNotEmpty()) {
                val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
                val student = Student(name, area, currentDate)
                students.add(student)
                adapter.add("$name - $area - $currentDate")
                adapter.notifyDataSetChanged()
                nomeEstudante.text.clear()
                areaEstudante.text.clear()
                updateCountDisplay()
            } else {
                Toast.makeText(this, "Por favor, preencha ambos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            students.clear()
            adapter.clear()
            updateCountDisplay()
        }

        updateDate()
    }

    private fun updateCountDisplay() {
        countDisplay.text = "Quantidade de alunos: ${students.size}"
    }

    private fun updateDate() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        dateDisplay.text = "Data atual: $currentDate"
    }

    private data class Student(val name: String, val area: String, val date: String)
}
