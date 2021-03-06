package com.JHOB.x00177919;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Empleado empleado;
    public static void main(String[] args) throws ErrorInputDataException, InputMismatchException {

        //datos utilizados
        int opcion = 0;
        int trabajo = 0;
        String nombreEmpleado = "";
        String puesto = "";
        double salario = 0.0;
        Empresa empresa;
        String nombreEmpresa= "";
        String nombreD = "";
        String numero = "";
        int mesesContrato = 0;
        int extension = 0;
        String eliminar = "";

        ArrayList<Empleado> em = new ArrayList<>();
        CalculadoraImpuestos imp = new CalculadoraImpuestos();
        ArrayList<Documento> dm = new ArrayList<>();

        //empieza la codificacion
        boolean cont = false;
        do {

            try {
                cont = false;
                System.out.print("Nombre de la empresa: ");
                nombreEmpresa = scan.nextLine();
                if (numeroenString(nombreEmpresa)) {
                    throw new NumberFormatException("No ingrese numeros");
                }
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
                cont = true;
            }
        }while (cont);
        empresa = new Empresa(nombreEmpresa);


        do {
            boolean continuar = false;
            do{
                try{
                    continuar = false;

                    System.out.print(menu());
                    opcion = scan.nextByte(); scan.nextLine();

                }
                catch (InputMismatchException ex){
                    scan.nextLine();
                    System.out.println("Ingrese numeros...");
                    continuar = true;
                }

            }
            while (continuar);

            switch (opcion) {
                case 1:
                    boolean flag = false;
                    do {
                        try {
                            flag = false;
                            System.out.print("\nNombre del empleado: ");
                            nombreEmpleado = scan.nextLine();
                            if (numeroenString(nombreEmpleado)) {
                                throw new NumberFormatException("No ingrese numeros");
                            }

                            System.out.print("Puesto que ejerce: ");
                            puesto = scan.nextLine();
                            if (numeroenString(puesto)) {
                                throw new NumberFormatException("No ingrese numeros");
                            }

                            flag = false;
                            System.out.print("\nContrato del empleado \n1.Servicio Profesional \n2.Plaza Fija"
                                    + " \nSu opcion: ");
                            trabajo = scan.nextByte();
                            scan.nextLine();

                            if (trabajo == 1) {
                                flag = true;
                                System.out.print("Digite salario: ");
                                salario = scan.nextDouble();
                                scan.nextLine();

                                System.out.print("Meses de servicio: ");
                                mesesContrato = scan.nextInt();
                                scan.nextLine();

                                if (salario <= 0) {
                                    throw new ErrorInvalidDataException("Ingrese un salario valido");
                                }

                                ServicioProfesional SP = new ServicioProfesional(nombreEmpleado, puesto,
                                        salario, mesesContrato);
                                empresa.addEmpleado(SP);

                                System.out.println("\nIngreso de identificacion");
                                System.out.print("Cuantos documetos desea ingresar? ");
                                int doc = scan.nextInt();
                                scan.nextLine();

                                for (int i = 0; i < doc; i++) {
                                    System.out.print("Nombre de identificacion: ");
                                    nombreD = scan.nextLine();
                                    if (numeroenString(nombreD)) {
                                        throw new NumberFormatException("No ingrese numeros");
                                    }

                                    System.out.print("Numero de identificacion: ");
                                    numero = scan.nextLine();

                                    String finalNombreEmpleado = nombreEmpleado;
                                    String finalNombreD = nombreD;
                                    String finalNumero = numero;
                                    empresa.getPlanilla().forEach(s -> {
                                        if(s.nombreEmpleado == finalNombreEmpleado){
                                            s.addDocumento(new Documento(finalNombreD, finalNumero));
                                        }
                                    });
                                }


                            } else if (trabajo == 2) {
                                flag = true;
                                System.out.print("Digite salario: ");
                                salario = scan.nextDouble();
                                scan.nextLine();
                                if (salario <= 0) {
                                    throw new ErrorInvalidDataException("Ingresar un salario valido");
                                }

                                System.out.print("Telefono: ");
                                extension = scan.nextInt();
                                scan.nextLine();

                                PlazaFija PF = new PlazaFija(nombreEmpleado, puesto, salario, extension);
                                empresa.addEmpleado(PF);

                                System.out.println("\nIngreso de identificacion");
                                System.out.print("Cuantos documetos desea ingresar? ");
                                int docu = scan.nextInt();
                                scan.nextLine();

                                for (int i = 0; i < docu; i++) {
                                    System.out.print("Nombre de identificacion: ");
                                    nombreD = scan.nextLine();
                                    if (numeroenString(nombreD)) {
                                        throw new NumberFormatException("No ingrese numeros");
                                    }

                                    System.out.print("Numero de identificacion: ");
                                    numero = scan.nextLine();

                                    String finalNombreEmpleado = nombreEmpleado;
                                    String finalNombreD = nombreD;
                                    String finalNumero = numero;
                                    empresa.getPlanilla().forEach(s -> {
                                        if(s.nombreEmpleado == finalNombreEmpleado){
                                            s.addDocumento(new Documento(finalNombreD, finalNumero));
                                        }
                                    });

                                }

                            }
                            if(flag == false){
                                throw new ErrorInvalidDataException("Valor invalido");
                            }

                            flag = false;

                        } catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            flag = true;
                        }
                        catch (InputMismatchException ex){
                           scan.nextLine(); System.out.println("Valor invalido");
                            flag = true;
                        }
                        catch (ErrorInvalidDataException ex) {
                            scan.nextLine(); System.out.println("El valor no concuerda");
                            flag= true;
                        }

                    }while (flag);
                    break;
                case 2:
                    em = empresa.getPlanilla();

                    System.out.print("Ingrese nombre del empleado a despedir: ");
                    eliminar = scan.nextLine();

                    String finalEliminar = eliminar;
                    em.removeIf(s-> s.nombreEmpleado.equalsIgnoreCase(finalEliminar));

                    empresa.setPlanilla(em);
                    break;
                case 3:
                    em = empresa.getPlanilla();

                    em.forEach(s -> System.out.println("\nDatos del empleado\n" + s.toString() + "\n"
                            + s.getDocumentos()));

                    break;
                case 4:
                    em = empresa.getPlanilla();
                    for (Empleado e : em) {
                        System.out.println("Empleado: " + e.nombreEmpleado);
                        System.out.println("Puesto: " + e.puesto);
                        System.out.println("Su sueldo es de: $" + imp.calcularPago(e));
                        System.out.println("\n");
                    }
                    break;
                case 5:
                    System.out.println(imp.mostrarTotales());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Valor incorrecto");
            }


        }
        while (opcion != 0);
    }

    static String menu(){
        return "\nMenu Principal\n" + "1.Agregar empleado\n" + "2.Despedir empleado\n" + "3.ver lista de empleados\n" +
                "4.Calcular sueldo\n" + "5.Mostrar totales\n" + "0.salir\n" + "Su opcion: ";
    }


    public static boolean numeroenString(String n) {
        char[] letras = n.toCharArray();
        char[] var2 = letras;
        int var3 = letras.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char v = var2[var4];
            if (Character.isDigit(v)) {
                return true;
            }
        }
        return false;
    }
}
