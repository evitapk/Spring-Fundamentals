package com.spotifyplaylist.controller;

import com.spotifyplaylist.model.dto.AddSongDTO;
import com.spotifyplaylist.service.SongServiceImpl;
import com.spotifyplaylist.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SongControllerImpl implements SongController {

    private final LoggedUser loggedUser;
    private final SongServiceImpl songService;

    public SongControllerImpl(LoggedUser loggedUser, SongServiceImpl songService) {
        this.loggedUser = loggedUser;
        this.songService = songService;
    }

    @Override
    public String addSong() {
        if (!loggedUser.isLogged()) {
            return "redirect:/users/login";
        }

        return "song-add";
    }

    @Override
    public String addSong(AddSongDTO addSongDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addSongDTO", addSongDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addSongDTO", result);

            return "redirect:/songs/add-song";
        }

        this.songService.addSong(addSongDTO);
        return "redirect:/home";
    }

    @ModelAttribute
    public AddSongDTO addSongDTO() {
        return new AddSongDTO();
    }
}
