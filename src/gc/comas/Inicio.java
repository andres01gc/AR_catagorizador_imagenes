package gc.comas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Inicio {
    private List<String> pathImagenes = new ArrayList<>();
    private List<String[]> datosLinea = new ArrayList<>();
    private ArrayList<String> categorias_botellas;

    Inicio() {
        cargarCsv("E:\\Gc\\Trabajo\\Arce\\Botellas\\code.csv");
        cargarPathsTodasImagenes("E:\\Gc\\Trabajo\\Arce\\Botellas\\Imagenes_Finales\\Todas\\normal"); //done!
        separarImagenesPorCategoria(new File("E:\\Gc\\Trabajo\\Arce\\Botellas\\Codes\\Resultados\\cropped"));
    }

    void separarImagenesPorCategoria(File saveFile) {
        //creo las carpetas donde reposarán las imagenes

        for (String _ruta_img : pathImagenes) {
//            System.out.println(_ruta_img);
            boolean existe_doc = false;
            for (String[] _datos_por_linea : datosLinea) {
                //comparo el nombre de la imagen con el guardado en el csv.
                if (new File(_ruta_img).getName().contains(_datos_por_linea[0].trim())) {
                    existe_doc = true;
                    //la imagen existe en el CSV!
                    //busco la categoria y la comparo con la imagen.

                    for (String l : categorias_botellas) {
                        //comparo si la categoria de la imagen existe en las categorias de las botellas
                        if (_datos_por_linea[1].equals(l)) {
                            try {
                                // copio la imagen a la nueva carpeta
                                File f = new File(saveFile.getPath() + "//" + l);

                                if (!f.exists()) {
                                    f.mkdir();
                                }
                                copyFile(new File(_ruta_img), new File(saveFile.getPath() + "//" + l + "//" + new File(_ruta_img).getName()));
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("parece que ese file ya existe");
                            }
                        }
                    }
                    break;
                }
            }
            System.out.println(new File(_ruta_img).getName());

            if (!existe_doc) {
                System.out.println("Parece que esta imagen no existe");
                String l = "Sin Clasificar";
                File f = new File(saveFile.getPath() + "//" + l);

                if (!f.exists()) {
                    f.mkdir();
                }

                try {
                    copyFile(new File(_ruta_img), new File(saveFile.getPath() + "//" + l + "//" + new File(_ruta_img).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void cargarPathsTodasImagenes(String pathCarpetaImagenes) {
        File principalFile = new File(pathCarpetaImagenes);

        for (File f : principalFile.listFiles()) {
            if (f.getName().toUpperCase().contains(".JPG")) {
//                System.out.println(f.getAbsolutePath());
                pathImagenes.add(f.getAbsolutePath());
            }
        }
        System.out.println("Se Cargan los path de las imagenes");
    }

    void cargarCsv(String path) {
        BufferedReader in = null;

        Set<String> _categorias_botellas = new HashSet<>();
        String line = null;
        boolean ttl = true;

        // Cargo el archivo csv
        try {
            in = new BufferedReader(new FileReader(path));

            line = in.readLine();//leo la primera linea de los titlos
            // divido la linea
            String[] partes_linea = line.split(",");
            //elimino los espacios de los titulos

            for (int i = 0; i < partes_linea.length; i++) {
                partes_linea[i] = partes_linea[i].trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean act = true;

        while (act) {
            try {
                if ((line = in.readLine()) != null) {
//                    System.out.println(line);
                    String[] data_partes = line.split(",");
//                    System.out.println(data_partes[0]);
                    datosLinea.add(data_partes);
                    _categorias_botellas.add(data_partes[1].trim());
                } else {
                    act = false;
                    System.out.println("Se finaliza de leer la ultima linea");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        categorias_botellas = new ArrayList<>(_categorias_botellas);

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("se cargan los datos csv");
    }

    static void copyFile(File from, File to) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }
}
