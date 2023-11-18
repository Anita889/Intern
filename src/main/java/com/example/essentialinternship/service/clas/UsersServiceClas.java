package com.example.essentialinternship.service.clas;

import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.UsersRepository;
import com.example.essentialinternship.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceClas implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceClas(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void saveUser(Users user) {
        if (isSaveAble(user))
            usersRepository.save(user);
        else
            throw new RepositoryException("Not valid user");
    }

    private boolean isSaveAble(Users user){
        return user.getUserId() != null
                && user.getAddress() != null
                && user.getAccounts() != null
                && user.getContactNumber() != null
                && user.getEmail() != null
                && user.getFirstName() != null
                && user.getLastName() != null
                && user.getDateOfBirth() != null
                && (usersRepository.findByEmail(user.getEmail())==null)
                && (usersRepository.findByPassword(user.getPassword())==null);
    }
    @Override
    public Users findById(Integer id) {
        if(usersRepository.findById(id).isPresent())
            return  usersRepository.findById(id).get();
            else
                throw new RepositoryException("User not find by id");
    }

    @Override
    public Users findByEmail(String email) {
        if(usersRepository.findByEmail(email)!=null)
        return usersRepository.findByEmail(email);
        else
            throw new RepositoryException("User not find bt email");
    }

    @Override
    public void updateUser(Users user,boolean verifyMessage) {
        Optional<Users> upDateUser=usersRepository.findById(user.getUserId());
        if(isSaveAble(user) && upDateUser.isPresent() && verifyMessage)
        {
                upDateUser.get().setUserId(user.getUserId());
                upDateUser.get().setAddress(user.getAddress());
                upDateUser.get().setAccounts(user.getAccounts());
                upDateUser.get().setContactNumber(user.getContactNumber());
                upDateUser.get().setEmail(user.getEmail());
                upDateUser.get().setFirstName(user.getFirstName());
                upDateUser.get().setLastName(user.getLastName());
                upDateUser.get().setDateOfBirth(user.getDateOfBirth());
                upDateUser.get().setPassword(user.getPassword());
                usersRepository.updateById(upDateUser, user.getUserId());
        }
        else throw new RepositoryException("Not valid updateAble user");
    }
}
