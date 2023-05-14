package com.derrick.services.DesignServiceImp;

import com.derrick.model.Desing;
import com.derrick.repository.DesignRepository;
import com.derrick.services.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesImp implements DesignService {
    @Autowired
    DesignRepository designrepo;
    @Override
    public Desing saveDesign(Desing desing) {
        return designrepo.save(desing);
    }

    @Override
    public Desing updateDesign(Desing desing) {
      return designrepo.save(desing);
    }

    @Override
    public Desing findDesign(Long id) {

        return designrepo.findById(id).get();
    }

    @Override
    public List<Desing> getAllDesigns() {
        return designrepo.findAll();
    }

    @Override
    public void deleteDesign(Long desing) {
        designrepo.deleteById(desing);
    }

    @Override
    public Page<Desing> pagenateStudent(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo -1,pageSize);
        return this.designrepo.findAll(pageable);
    }
}
