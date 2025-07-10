#include <stdio.h>
#include <string.h>

void addStudent()
{
    FILE *ptr1 = fopen("student.txt", "r");
    FILE *ptr;

    if (ptr1 == NULL)
    {
        printf("\nFile not found.... Creating new file...\n");
        ptr = fopen("student.txt", "w");
        if (ptr == NULL)
        {
            printf("\nUnable to create file.\n");
            return;
        }
        else
        {
            printf("\nFile created successfully.\n");
        }
    }
    else
    {
        printf("\n----Enter Informations----\n");
        ptr = fopen("student.txt", "a");
    }

    fclose(ptr1);

    int roll;
    printf("Enter roll No.: ");
    scanf("%d", &roll);

    char fname[20], lname[20];
    printf("Enter First Name: ");
    getchar();
    fgets(fname, sizeof(fname), stdin);
    fname[strcspn(fname, "\n")] = '\0';

    printf("Enter Last Name: ");
    fgets(lname, sizeof(lname), stdin);
    lname[strcspn(lname, "\n")] = '\0';

    float marks;
    printf("Enter marks: ");
    scanf("%f", &marks);

    char result[10];
    if(marks >= 40){
        strcpy(result, "Pass");
    } else {
        strcpy(result, "Fail");
    }

    // printf("Enter result: ");
    // getchar();
    // fgets(result, sizeof(result), stdin);
    // result[strcspn(result, "\n")] = '\0';

    fprintf(ptr, "%d %s %s %.2f %s\n", roll, fname, lname, marks, result);
    fclose(ptr);
}

void displayInfo()
{
    FILE *ptr = fopen("student.txt", "r");

    if (ptr == NULL)
    {
        printf("\nFile not Found....!\n");
        return;
    }

    printf("\n--------------- Student Informaton ----------------");
    int roll;
    char fname[20], lname[20], result[10];
    float marks;

    printf("\n%-10s %-20s %-10s %-10s\n", "Roll No.", "Name", "Marks", "Result");
    printf("---------------------------------------------------\n");

    while (fscanf(ptr, "%d %s %s %f %s", &roll, fname, lname, &marks, result) != EOF)
    {
        printf("%-10d %-20s %-10.2f %-10s\n", roll, strcat(strcat(fname, " "), lname), marks, result);
    }

    fclose(ptr);
}

void searchStudent()
{
    FILE *ptr = fopen("student.txt", "r");

    if (ptr == NULL)
    {
        printf("\nFile not Found....!\n");
        return;
    }

    int searchRoll;
    printf("Enter roll No.: ");
    scanf("%d", &searchRoll);

    int roll;
    char fname[20], lname[20], result[10];
    float marks;
    int found = 0;

    while (fscanf(ptr, "%d %s %s %f %s", &roll, fname, lname, &marks, result) != EOF)
    {
        if (roll == searchRoll)
        {
            printf("\nRoll No.: %d\n", roll);
            printf("Name: %s %s\n", fname, lname);
            printf("Marks: %.2f\n", marks);
            printf("Result: %s\n", result);
            found = 1;
            break;
        }
    }

    if (found != 1)
    {
        printf("\nNot founds..!\n");
    }

    fclose(ptr);
}

void updateInfo()
{
    FILE *ptr = fopen("student.txt", "r");

    if (ptr == NULL)
    {
        printf("\nFile not Found....!\n");
        return;
    }

    FILE *temp = fopen("temp.txt", "w");

    int targetRoll;
    printf("Enter roll No. to update: ");
    scanf("%d", &targetRoll);

    char newFname[20], newLname[20], newResult[20];
    float newMarks;

    printf("\n---Which things you Update---\n");
    printf("\n1. Name\n");
    printf("2. Marks\n");
    printf("3. All of these\n");

    int f;
    printf("Enter your Option: ");
    scanf("%d", &f);

    switch (f)
    {
    case 1:
        printf("Enter New First Name: ");
        getchar();
        fgets(newFname, sizeof(newFname), stdin);
        newFname[strcspn(newFname, "\n")] = '\0';

        printf("Enter New Last Name: ");
        fgets(newLname, sizeof(newLname), stdin);
        newLname[strcspn(newLname, "\n")] = '\0';
        break;

    case 2:
        printf("Enter New Marks: ");
        scanf("%f", &newMarks);
        break;

    case 3:
        printf("Enter New First Name: ");
        getchar();
        fgets(newFname, sizeof(newFname), stdin);
        newFname[strcspn(newFname, "\n")] = '\0';

        printf("Enter New Last Name: ");
        fgets(newLname, sizeof(newLname), stdin);
        newLname[strcspn(newLname, "\n")] = '\0';

        printf("Enter New Marks: ");
        scanf("%f", &newMarks);

    default:
        printf("\n---Enter only 1 to 3---\n");
        break;

    }

    if(newMarks >= 40){
        strcpy(newResult, "Pass");
    } else {
        strcpy(newResult, "Fail");
    }

    int roll;
    char fname[20], lname[20], result[20];
    float marks;

    while (fscanf(ptr, "%d %s %s %f %s", &roll, fname, lname, &marks, result) != EOF)
    {
        if (roll == targetRoll)
        {
            switch (f)
            {
            case 1:
                fprintf(temp, "%d %s %s %.2f %s\n", roll, newFname, newLname, marks, result);
                break;

            case 2:
                fprintf(temp, "%d %s %s %.2f %s\n", roll, fname, lname, newMarks, newResult);
                break;
            
            case 3:
                fprintf(temp, "%d %s %s %.2f %s\n", roll, newFname, newLname, newMarks, newResult);
                break;

            default:
                printf("\n---Not Update any Information---\n");
                break;
            }
        }
        else
        {
            fprintf(temp, "%d %s %s %.2f %s\n", roll, fname, lname, marks, result);
        }
    }

    fclose(ptr);
    fclose(temp);
    remove("student.txt");
    rename("temp.txt", "student.txt");
}

void calAvgTotal()
{
    FILE *ptr;
    ptr = fopen("student.txt", "r");

    if (ptr == NULL)
    {
        printf("\nFile not Found....!\n");
        return;
    }

    int roll;
    char fname[20], lname[20], result[20];
    float marks;
    float total = 0;
    int a = 0;

    while (fscanf(ptr, "%d %s %s %f %s", &roll, fname, lname, &marks, result) != EOF)
    {
        total += marks;
        a++;
    }

    float avg = total / a;
    printf("Total: %.2f\n", total);
    printf("Average: %.2f", avg);
}

int main()
{

    while (1)
    {

        int a;
        printf("\n-------- Options --------\n");
        printf("\n1. Add student records\n");
        printf("2. Display all records\n");
        printf("3. Search by roll number\n");
        printf("4. Update and delete records\n");
        printf("5.Calculate total and average marks\n");
        printf("6. Exit\n");
        printf("\nChoose one Options: ");
        scanf("%d", &a);

        switch (a)
        {
        case 1:
            addStudent();
            break;

        case 2:
            displayInfo();
            break;

        case 3:
            searchStudent();
            break;

        case 4:
            updateInfo();
            break;

        case 5:
            calAvgTotal();
            break;

        case 6:
            return 0;

        default:
            printf("\n-------Enter 1 to 6-------\n");
        }
    }

    return 0;
}