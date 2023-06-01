package pe.upc.touristgermany.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.upc.touristgermany.business.BusinessPostulant;
import pe.upc.touristgermany.dtos.PostulantDTO;
import pe.upc.touristgermany.entities.Postulant;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestPostulant {

    @Autowired
    private BusinessPostulant businessPostulant;

    //INSERT
    @PostMapping("/postulant")
    public PostulantDTO insert(@RequestBody PostulantDTO postulantDTO){
        Postulant postulant;
        try{
            postulant = convertToEntity(postulantDTO);
            postulant = businessPostulant.insert(postulant);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro fallido");
        }
        return convertToDto(postulant);
    }

    //LIST
    @GetMapping("/postulant")
    public ResponseEntity<List<PostulantDTO>> list(){
        List<PostulantDTO> listPostulantDTO;
        try{
            listPostulantDTO=convertToLisDto(businessPostulant.list());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No fue posible listar");
        }
        return new ResponseEntity<>(listPostulantDTO, HttpStatus.OK);
    }

    //UPDATE
    @PutMapping("/postulant/{id}")
    public ResponseEntity<PostulantDTO> update(@PathVariable(value = "id") Long id, @RequestBody PostulantDTO postulantDTO){
        Postulant postulant;
        Postulant postulantUpdate;
        try{
            postulant = convertToEntity(postulantDTO);
            postulantUpdate = businessPostulant.update(id,postulant);
            postulantDTO = convertToDto(postulantUpdate);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible actualizar");
        }
        return new ResponseEntity<PostulantDTO>(postulantDTO, HttpStatus.OK);
    }

    //LIST STATUS: SINGLE
    @GetMapping("/postulant/{status}")
    public ResponseEntity<List<PostulantDTO>> listStatus(@PathVariable(value = "status")String status){
        List<Postulant> postulant;
        List<PostulantDTO> postulantDTO;
        try{
            postulant=businessPostulant.listSingle(status);
            postulantDTO=convertToLisDto(postulant);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible obtener listado");
        }
        return new ResponseEntity<>(postulantDTO,HttpStatus.OK);
    }

    //CALIFICATION
    @GetMapping("/postulantResult")
    public List<PostulantDTO> listPostulantCalification(){
        List<PostulantDTO> listPostulantDTO;
        try{
            listPostulantDTO = businessPostulant.listPostulantCalification();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible listar resultados");
        }
        return listPostulantDTO;
    }

    //SEARCH BY DNI --> RESULT
    @GetMapping("/postulant/dni/{dni}")
    public ResponseEntity<String> calculateCalificationByDNI(@PathVariable(value = "dni") String dni) {
        String result;
        try {
            result = businessPostulant.calculateCalificationByDNI(dni);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No fue posible encontrar su registro");
        }
        return new ResponseEntity<String>(result,HttpStatus.OK);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //DTO
    private PostulantDTO convertToDto(Postulant postulant) {
        ModelMapper modelMapper = new ModelMapper();
        PostulantDTO postulantDTO = modelMapper.map(postulant, PostulantDTO.class);
        return postulantDTO;
    }
    private Postulant convertToEntity(PostulantDTO postulantDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Postulant post = modelMapper.map(postulantDTO, Postulant.class);
        return post;
    }
    private List<PostulantDTO> convertToLisDto(List<Postulant> list){
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



}
