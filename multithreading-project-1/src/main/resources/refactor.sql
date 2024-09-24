-- before sql :

CREATE OR REPLACE PROCEDURE HR_Delete_OCCalendar_All_pr
(tablename  IN nvarchar2,
Calendar_Id IN NUMBER ,
validFrom IN nvarchar2,
validTo IN nvarchar2,
hrc IN nvarchar2,
nrows IN NUMBER ) IS

sSQL1 VARCHAR2(20000);
sSQL2 VARCHAR2(20000);
nCount NUMBER;

BEGIN


nCount := 0;
sSQL1:='delete from '|| REPLACE(tablename,'""','') ||
' where ROWNUM < ' || nrows ||/* ' and Oc_Calendar_Id=' || Calendar_Id ||*/
' and dateCurrent>= '|| validFrom || ' and dateCurrent<= ' || validTo||
' and Hr_c_Bpartners_Id in(' || hrc ||')' ;


sSQL2:='select count(ROWID) from ' || REPLACE(tablename,'""','') ||
' where Oc_Calendar_Id=' || Calendar_Id ||
' and dateCurrent>= '|| validFrom || ' and dateCurrent<= ' || validTo ||
' and Hr_c_Bpartners_Id in(' || hrc ||')' ;
LOOP

EXECUTE IMMEDIATE sSQL1;

EXECUTE IMMEDIATE sSQL2 INTO nCount;

DBMS_OUTPUT.PUT_LINE('Existing records: ' || to_char(ncount) );

commit;

EXIT WHEN nCount = 0;

END LOOP;

END HR_Delete_OCCalendar_All_pr;

-- after sql , optimized code:
CREATE OR REPLACE PROCEDURE HR_Delete_OCCalendar_All_pr
(
    tablename   IN NVARCHAR2,
    Calendar_Id IN NUMBER,
    validFrom   IN NVARCHAR2,
    validTo     IN NVARCHAR2,
    hrc         IN NVARCHAR2,
    nrows       IN NUMBER
) IS
    sSQL1   VARCHAR2(20000);
    sSQL2   VARCHAR2(20000);
    nCount  NUMBER;
BEGIN
    nCount := 0;

    sSQL1 := 'delete from ' || REPLACE(tablename, '""', '') ||
             ' where ROWNUM < :nrows' ||
             ' and dateCurrent >= TO_DATE(:validFrom, ''YYYY-MM-DD'')' ||
             ' and dateCurrent <= TO_DATE(:validTo, ''YYYY-MM-DD'')' ||
             ' and Hr_c_Bpartners_Id in (' || hrc || ')';

    sSQL2 := 'select count(ROWID) from ' || REPLACE(tablename, '""', '') ||
             ' where Oc_Calendar_Id = :Calendar_Id' ||
             ' and dateCurrent >= TO_DATE(:validFrom, ''YYYY-MM-DD'')' ||
             ' and dateCurrent <= TO_DATE(:validTo, ''YYYY-MM-DD'')' ||
             ' and Hr_c_Bpartners_Id in (' || hrc || ')';

    LOOP
EXECUTE IMMEDIATE sSQL1 USING nrows, validFrom, validTo;
EXECUTE IMMEDIATE sSQL2 INTO nCount USING Calendar_Id, validFrom, validTo;

DBMS_OUTPUT.PUT_LINE('Existing records: ' || TO_CHAR(nCount));

        EXIT WHEN nCount = 0 OR nCount IS NULL;

COMMIT;
END LOOP;

COMMIT; -- Commit after the entire loop finishes
END HR_Delete_OCCalendar_All_pr;
