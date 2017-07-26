package com.mobile.mpasswordkeeper;

import android.content.Context;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.database.EmailDetails;
import com.mobile.mpasswordkeeper.database.EmailDetailsDao;
import com.mobile.mpasswordkeeper.database.OtherDetails;
import com.mobile.mpasswordkeeper.database.OtherDetailsDao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by 836137 on 10-07-2017.
 */

public class Utility {

    public static final String BANK_ID = "bankId";
    public static final String OTHER_ID = "otherId";
    public static String EMAIL_ID = "emailId";

    public static View inflateViewtoStub(ViewStubCompat viewStubCompat, int layout) {
        viewStubCompat.setLayoutResource(layout);
        View view = viewStubCompat.inflate();
        viewStubCompat.setVisibility(View.VISIBLE);
        return view;
    }

    public static void readFileforBank(File file, BankDetailsDao bankDetailsDao, CardDetailsDao cardDetailsDao, Context context) {
        try {

            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            boolean isOnline = false;
            boolean isCredit = false;
            boolean isDebit = false;
            String error = "";
            //for the rows
            //move to 1st row
            if (iterator.hasNext()) {
                Row firstRow = iterator.next();
            }

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Log.d("this", String.valueOf(currentRow.getLastCellNum()));

                BankDetails bankDetails = new BankDetails();
                CardDetails debitCardDetails = new CardDetails();
                CardDetails creditCardDetails = new CardDetails();
             /*
                 Iterator<Cell> cellIterator = currentRow.iterator();
                    while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                }*/
                if (currentRow.getCell(0).getStringCellValue() != null)
                    bankDetails.setTitle(currentRow.getCell(0).getStringCellValue());
                if (currentRow.getCell(1).getStringCellValue() != null)
                    bankDetails.setBankName(currentRow.getCell(1).getStringCellValue());
                else
                    error += "No Bank Name for Line" + currentRow.getRowNum() + "\n";
                if (currentRow.getCell(2).getNumericCellValue() != 0)
                    bankDetails.setAccountNo(String.valueOf(currentRow.getCell(2).getNumericCellValue()));
                else
                    error += "No Bank Account Number for Line" + currentRow.getRowNum() + "\n";
                if (currentRow.getCell(3).getStringCellValue() != null)
                    bankDetails.setIfsc(currentRow.getCell(3).getStringCellValue());
                if (currentRow.getCell(4).getStringCellValue() != null)
                    bankDetails.setBankBranch(currentRow.getCell(4).getStringCellValue());
                else
                    error += "No Branch name for Line" + currentRow.getRowNum() + "\n";
                if (currentRow.getCell(5).getStringCellValue().equalsIgnoreCase("Y")) {
                    isOnline = true;
                    if (currentRow.getCell(8).getStringCellValue() != null)
                        bankDetails.setOnlineUsername(currentRow.getCell(8).getStringCellValue());
                    else
                        error += "No Online username for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(9).getStringCellValue() != null)
                        bankDetails.setOnlinePassword(currentRow.getCell(9).getStringCellValue());
                    else
                        error += "No Online password for Line" + currentRow.getRowNum() + "\n";
                }
                if (currentRow.getCell(6).getStringCellValue().equalsIgnoreCase("Y")) {
                    isCredit = true;
                    if (currentRow.getCell(10).getNumericCellValue() != 0)
                        creditCardDetails.setCardNumber((long) currentRow.getCell(10).getNumericCellValue());
                    else
                        error += "No Credit Card number for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(11).getStringCellValue() != null)
                        creditCardDetails.setExpiryDate(new SimpleDateFormat("MM/yy").parse(currentRow.getCell(11)
                                .getStringCellValue()));
                    else
                        error += "No Credit Card expiry date for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(12).getNumericCellValue() != 0)
                        creditCardDetails.setCVV((int) currentRow.getCell(12).getNumericCellValue());
                    else
                        error += "No Credit Card CVV for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(13).getStringCellValue() != null)
                        creditCardDetails.setCardName(currentRow.getCell(13).getStringCellValue());
                    else
                        error += "No Credit Card number for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(14).getNumericCellValue() != 0)
                        creditCardDetails.setCardPin((int) currentRow.getCell(14).getNumericCellValue());
                    else
                        error += "No Credit Card pin for Line" + currentRow.getRowNum() + "\n";
                    creditCardDetails.setCardType(CardDetails.CardType.CREDIT);

                }
                if (currentRow.getCell(7).getStringCellValue().equalsIgnoreCase("Y")) {
                    isDebit = true;
                    if (currentRow.getCell(15).getNumericCellValue() != 0)
                        debitCardDetails.setCardNumber((long) currentRow.getCell(15).getNumericCellValue());
                    else
                        error += "No Debit Card number for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(16).getStringCellValue() != null)
                        try {
                            debitCardDetails.setExpiryDate(new SimpleDateFormat("MM/yy").parse(currentRow.getCell(16)
                                    .getStringCellValue()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    else
                        error += "No Debit Card expiry date for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(17).getNumericCellValue() != 0)
                        debitCardDetails.setCVV((int) currentRow.getCell(17).getNumericCellValue());
                    else
                        error += "No Debit Card CVV for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(18).getStringCellValue() != null)
                        debitCardDetails.setCardName(currentRow.getCell(18).getStringCellValue());
                    else
                        error += "No Debit Card number for Line" + currentRow.getRowNum() + "\n";
                    if (currentRow.getCell(19).getNumericCellValue() != 0)
                        debitCardDetails.setCardPin((int) currentRow.getCell(19).getNumericCellValue());
                    else
                        error += "No Debit Card pin for Line" + currentRow.getRowNum() + "\n";
                    debitCardDetails.setCardType(CardDetails.CardType.DEBIT);
                }

                if (error != null && !(error.length() > 0)) {
                    long bankId = bankDetailsDao.insert(bankDetails);
                    if (isCredit) {
                        creditCardDetails.setBankId(bankId);
                        cardDetailsDao.insert(creditCardDetails);
                    }
                    if (isDebit) {
                        debitCardDetails.setBankId(bankId);
                        cardDetailsDao.insert(debitCardDetails);
                    }
                    Toast.makeText(context, "Row inserted : " + currentRow.getRowNum(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFileforEmail(File file, EmailDetailsDao emailDetailsDao, Context context) {

        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String error = "";
            if (iterator.hasNext()) {
                Row firstRow = iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                EmailDetails emailDetails = new EmailDetails();
                Log.d("this", String.valueOf(currentRow.getLastCellNum()));
                if(currentRow.getCell(0).getStringCellValue()!=null)
                    emailDetails.setEmailId(currentRow.getCell(0).getStringCellValue());
                else
                    error += "No Email ID for Line" + currentRow.getRowNum() + "\n";
                if(currentRow.getCell(1).getStringCellValue()!=null)
                    emailDetails.setPassword(currentRow.getCell(1).getStringCellValue());
                else
                    error += "No Email password for Line" + currentRow.getRowNum() + "\n";
                if(currentRow.getCell(2).getStringCellValue()!=null)
                    emailDetails.setAlternateEmail(currentRow.getCell(2).getStringCellValue());
                if(!(error.length() >0)){
                    emailDetailsDao.insert(emailDetails);
                }
                else{
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readExcelforOther(File file, OtherDetailsDao otherDetailsDao,Context context){
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String error = "";
            if (iterator.hasNext()) {
                Row firstRow = iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                OtherDetails otherDetail = new OtherDetails();
                Log.d("this", String.valueOf(currentRow.getLastCellNum()));
                if (currentRow.getCell(0).getStringCellValue() != null)
                    otherDetail.setTitle(currentRow.getCell(0).getStringCellValue());
                if(currentRow.getCell(1).getStringCellValue()!=null)
                    otherDetail.setUserName(currentRow.getCell(0).getStringCellValue());
                else
                    error += "No Username for Line" + currentRow.getRowNum() + "\n";
                if(currentRow.getCell(2).getStringCellValue()!=null)
                    otherDetail.setPassword(currentRow.getCell(2).getStringCellValue());
                else
                    error += "No Password for Line" + currentRow.getRowNum() + "\n";
                if(currentRow.getLastCellNum()>3)
                if (currentRow.getCell(3).getStringCellValue() != null)
                    otherDetail.setAdditionalInfo(currentRow.getCell(3).getStringCellValue());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
