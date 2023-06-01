package pe.upc.touristgermany.business;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.upc.touristgermany.dtos.PostulantDTO;
import pe.upc.touristgermany.entities.Postulant;
import pe.upc.touristgermany.repositories.RepositoryPostulant;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessPostulant {
    @Autowired
    private RepositoryPostulant repositoryPostulant;

    //INSERT
    public Postulant insert(Postulant postulant){
        Postulant postulant1 = repositoryPostulant.save(postulant);
        return postulant1;
    }

    //LIST
    public List<Postulant> list(){
        return repositoryPostulant.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    //UPDATE
    public Postulant update(Long id, Postulant postulantUpdate){
        Postulant postulantNow = repositoryPostulant.findById(id).get();
        postulantUpdate.setId(id);//verificar q existe
        return repositoryPostulant.save(postulantUpdate);//Actualizar con el enviado
    }

    //LIST STATUS: SINGLE
    public List<Postulant> listSingle(String status){
        return repositoryPostulant.findByStatus(status);//En repositoires
    }

    //CALIFICATION
    public Double calculateCalification(PostulantDTO postulantDTO){

        double civilStatus = 0.0;
        Integer travelBag = 0;

        if (postulantDTO.getStatus().matches("Soltero|Conviviente|Divorciado|Viudo|soltero|conviviente|divorciado|viudo")){
            civilStatus = 0.5;
        } else {
            civilStatus = 1.0;
        }

        if (postulantDTO.isBag()){
            travelBag=20;
        } else{
            travelBag=0;
        }

        return((postulantDTO.getAge()*civilStatus)+(postulantDTO.getSalary()/1000.0)+(travelBag));
    }

    public String calculateCalificationFinal(PostulantDTO postulantDTO){
        if (calculateCalification(postulantDTO)>=60){
            return "Elegible";
        } else {
            return "No elegible";
        }
    }

    public String calculateCalificationByDNI(String dni){
        Postulant postulant = repositoryPostulant.findByDni(dni);
        PostulantDTO postulantDTO = convertToDto(postulant);
        return calculateCalificationFinal(postulantDTO);
    }

    public List<PostulantDTO> listPostulantCalification(){
        List<Postulant> postulant;
        postulant = list();
        List<PostulantDTO> postulantDTO;
        postulantDTO = convertToLisDto(postulant);

        for (PostulantDTO p:postulantDTO){
            p.setCalification(calculateCalification(p));
        }

        return postulantDTO;

    }



    //----------------------------------------------------------------------------------------------------------------
    //DTO
    private PostulantDTO convertToDto(Postulant postulant) {
        ModelMapper modelMapper = new ModelMapper();
        PostulantDTO postulantDTO = modelMapper.map(postulant, PostulantDTO.class);
        return postulantDTO;
    }
    private List<PostulantDTO> convertToLisDto(List<Postulant> list){
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
