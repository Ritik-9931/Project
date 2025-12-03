#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void addData()
{
    FILE *db, *db1;

    db1 = fopen("database.txt", "r");
    if (db1 == NULL)
    {
        printf("File not found.........\nCreate new file...");
        db = fopen("database.txt", "w");
    }
    else
    {
        db = fopen("database.txt", "a");
    }
    fclose(db1);

    char fname[20];
    printf("Enter first name: ");
    getchar();
    fgets(fname, sizeof(fname), stdin);
    fname[strcspn(fname, "\n")] = '\0';

    char lname[20];
    printf("Enter last name: ");
    fgets(lname, sizeof(lname), stdin);
    lname[strcspn(lname, "\n")] = '\0';

    int id;
    printf("Enter you id: ");
    scanf("%d", &id);

    char email[50];
    printf("Enter your email: ");
    getchar();
    fgets(email, sizeof(email), stdin);
    email[strcspn(email, "\n")] = '\0';

    fprintf(db, "%s %s %d %s\n", fname, lname, id, email);
    fclose(db);
}

void viewData()
{
    FILE *db = fopen("database.txt", "r");

    if (db == NULL)
    {
        printf("\nFile not Found....!\n");
        return;
    }
    else
    {
        printf("\n-------------Information------------\n");
        char fname[20];
        char lname[20];
        int id;
        char email[50];

        printf("\n %-20s %-10s %s\n", "Name", "ID", "Email");
        printf("------------------------------------\n");

        while (fscanf(db, "%s %s %d %s", fname, lname, &id, email) != EOF)
        {
            printf("%-20s %-10d %s\n", strcat(strcat(fname, " "), lname), id, email);
        }
    }
    fclose(db);
}

void findData()
{
    FILE *db;
    db = fopen("database.txt", "r");

    int id;
    printf("Enter your id: ");
    scanf("%d", &id);

    char fname[20];
    char lname[20];
    int oldId;
    char email[50];

    int find = 0;
    while (fscanf(db, "%s %s %d %s", fname, lname, &oldId, email) != EOF)
    {
        if (id == oldId)
        {
            printf("%s %d %s", strcat(strcat(fname, " "), lname), oldId, email);
            find = 1;
        }
    }

    if (!find)
    {
        printf("Data not found...!");
    }
    fclose(db);
}

void updateData()
{
    FILE *db, *temp;
    db = fopen("database.txt", "r");
    temp = fopen("temp.txt", "w");

    int id;
    printf("Enter id for update: ");
    scanf("%d", &id);

    char fname[20];
    char lname[20];
    int oldId;
    char email[50];

    char newFname[20];
    char newLname[20];
    char newEmail[50];

    while (fscanf(db, "%s %s %d %s", fname, lname, &oldId, email) != EOF)
    {
        if (id == oldId)
        {
            printf("Enter first name: ");
            getchar();
            fgets(newFname, sizeof(newFname), stdin);
            newFname[strcspn(newFname, "\n")] = '\0';

            printf("Enter last name: ");
            fgets(newLname, sizeof(newLname), stdin);
            newLname[strcspn(newLname, "\n")] = '\0';

            printf("Enter your email: ");
            getchar();
            fgets(newEmail, sizeof(newEmail), stdin);
            newEmail[strcspn(newEmail, "\n")] = '\0';

            fprintf(temp, "%s %s %d %s\n", newFname, newLname, oldId, newEmail);
        }
        else
        {
            fprintf(temp, "%s %s %d %s\n", fname, lname, oldId, email);
        }
    }

    fclose(db);
    fclose(temp);
    remove("database.txt");
    rename("temp.txt", "database.txt");
}

void deleteData()
{
    FILE *db, *temp;
    db = fopen("database.txt", "r");
    temp = fopen("temp.txt", "w");

    int id;
    printf("Enter id for update: ");
    scanf("%d", &id);

    char fname[20];
    char lname[20];
    int oldId;
    char email[50];

    while (fscanf(db, "%s %s %d %s", fname, lname, &oldId, email) != EOF)
    {
        if (id == oldId)
        {
            continue;
        }
        else
        {
            fprintf(temp, "%s %s %d %s\n", fname, lname, oldId, email);
        }
    }

    fclose(db);
    fclose(temp);
    remove("database.txt");
    rename("temp.txt", "database.txt");
}

int main()
{
    while (1)
    {
        int choice;
        printf(
            "\n========DataBase========\n"
            "1. Add Data\n"
            "2. View Data\n"
            "3. Find Data\n"
            "4. Update Data\n"
            "5. Delete Data\n"
            "6. Exit\n");
        printf("Choose option:");
        scanf("%d", &choice);

        switch (choice)
        {
        case 1:
            addData();
            break;

        case 2:
            viewData();
            break;

        case 3:
            findData();
            break;

        case 4:
            updateData();
            break;

        case 5:
            deleteData();
            break;
        case 6:
            exit(0);
            break;

        default:
            printf("\n==========Invalid Input===========\n");
            break;
        }
    }

    return 0;
}