package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.CheckPasswordService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteAllTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final DeleteAllTablesService deleteAllTablesService;
    private final CheckPasswordService checkPasswordService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/data")
    public void deleteAllTables() {
        deleteAllTablesService.execute();
    }

    @GetMapping("/auth")
    public String execute(@RequestParam String password) {
        return checkPasswordService.execute(password);
    }

}
