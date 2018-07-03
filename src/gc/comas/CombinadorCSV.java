package gc.comas;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinadorCSV {
    private List<String> titulos = new ArrayList<>();

    private String[] titulos_columnas_b;

    private List<List<String>> datos_filas_target = new ArrayList<>();
    private List<List<String>> datos_filas_b = new ArrayList<>();

    CombinadorCSV() {
        //El archivo al que se le agregaràn los detalles serà el csv, que contiene la informaciòn fìsica
        cargarCSV_Target("E:\\Gc\\Trabajo\\Arce\\Botellas\\Codes\\Java\\Organizador_de_imagenes\\data\\code.csv");
        cargarCSV_B("E:\\Gc\\Trabajo\\Arce\\Botellas\\Codes\\Java\\Organizador_de_imagenes\\data\\BottleShapeInfo.csv");

        combinar();
        guardarDatos();
    }

    private void guardarDatos() {
        // The name of the file to open.
        String fileName = "data/datos_combinados.csv";

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                    new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.

            //agrego primero los titulos
            String primeraLinea = "";
            for (String titulo : titulos) {
                primeraLinea += titulo + ",";
            }

            bufferedWriter.write(primeraLinea);
            bufferedWriter.newLine();

            for (List<String> fila : datos_filas_target) {
                String linea = "";
                for (String dato : fila) {
                    linea += dato + ",";
                }
                bufferedWriter.write(linea);
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }

    private void combinar() {
        //combino los titulos

//comparar los nombres de cada fila, que se encuentran en la primera columba
        for (int i = 0; i < datos_filas_target.size(); i++) {
            for (int j = 0; j < datos_filas_b.size(); j++) {
                if (datos_filas_b.get(j).get(0).contains(datos_filas_target.get(i).get(0))) {
                    //las dos filas contienen el mismo nombre :D
                    List<String> d = new ArrayList<String>(datos_filas_target.get(i));
                    List<String> b = new ArrayList<String>(datos_filas_b.get(j));
                    b.remove(0);
                    //agregaomos los datos del b al target
                    d.addAll(b);
                    datos_filas_target.set(i, d);
                }
            }
        }
    }

    public void cargarCSV_Target(String path) {
        BufferedReader in = null;

        String line = null;
        boolean ttl = true;

        // Cargo el archivo csv
        try {
            in = new BufferedReader(new FileReader(path));

            line = in.readLine();//leo la primera linea de los titlos
            // divido la linea
            titulos.addAll(Arrays.asList(line.split(",")));
            //elimino los espacios de los titulos

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean act = true;
        while (act) {
            try {
                if ((line = in.readLine()) != null) {
                    String[] data_partes = line.split(",");
                    datos_filas_target.add(Arrays.asList(data_partes));
                } else {
                    act = false;
                    System.out.println("Se finaliza de leer la ultima linea");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("se cargan los datos csv");
    }


    public void cargarCSV_B(String path) {
        BufferedReader in = null;

        String line = null;
        boolean ttl = true;

        // Cargo el archivo csv
        try {
            in = new BufferedReader(new FileReader(path));

            line = in.readLine();//leo la primera linea de los titlos

            // divido la linea'

//            titulos.addAll();

            List<String> s = new ArrayList<String>(Arrays.asList(line.split(",")));
// elimino el primer titulo
            s.remove(0);
            titulos.addAll(s);

            //limpio los titulos
            for (int i = 0; i < titulos.size(); i++) {
                titulos.set(i, titulos.get(i).trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean act = true;
        while (act) {
            try {
                if ((line = in.readLine()) != null) {
                    String[] data_partes = line.split(",");
                    datos_filas_b.add(Arrays.asList(data_partes));
                } else {
                    act = false;
                    System.out.println("Se finaliza de leer la ultima linea");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("se cargan los datos csv");
    }

}
