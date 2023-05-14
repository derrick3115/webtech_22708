package com.derrick.services;



import com.derrick.model.Desing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DesignService {
    public Desing saveDesign(Desing desing);
    public Desing updateDesign(Desing desing);
    public Desing findDesign(Long id);
    public List<Desing> getAllDesigns();
    public void deleteDesign(Long id);
    Page<Desing> pagenateStudent(int pageNo, int pageSize);
}
