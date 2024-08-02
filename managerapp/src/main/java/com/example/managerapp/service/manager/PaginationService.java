package com.example.managerapp.service.manager;
/*  expense-parent
    01.08.2024
    @author DiachenkoDanylo
*/

import java.util.List;
import java.util.Map;

public abstract class PaginationService<T> {

    public abstract List<T> getAllObjectsWithPagination(int page, int clientsPerPage);

    public abstract List<T> getAllObjectsWithPagination(List<T> res, int page, int clientsPerPage);

    public abstract List<T> paginationConvert(List<T> res, int page, int objectsPerPage);

    public abstract int getPages(List<T> res, int objectPerPage);

    public abstract Map<String,Integer> pageAttributes(List<T> res, int page, int objectsPerPage);


}
