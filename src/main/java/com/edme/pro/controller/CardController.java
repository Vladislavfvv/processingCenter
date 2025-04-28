package com.edme.pro.controller;

import com.edme.pro.dto.CardDto;
import com.edme.pro.service.CardDtoSpringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000") - настройка на определенный порт
//в classе WebConfig можно настроить конкретные пути  ->  .allowedOrigins( // "https://myfrontend.com",
// и       "http://localhost:3000"


//ResponseEntity — это обёртка для HTTP-ответа (с кодом ответа, телом и заголовками)
@RestController// то же что и @Controller + @ResponseBody
// То есть, все методы автоматически возвращают JSON/объект, а не страницу HTML
@RequestMapping("/api/cards")//Все методы внутри будут начинаться с пути /api/cards
public class CardController {
    private final CardDtoSpringService cardService;//Поле сервиса. Через него контроллер будет общаться с бизнес-логикой

    @Autowired//@Autowired говорит Spring'у: "подставь сюда готовый экземпляр CardDtoSpringService
    //Это внедрение зависимостей (Dependency Injection)(вставить готовый сервис в контроллер).
    //Используется через конструктор — это "чистый" способ инъекции.
    public CardController(CardDtoSpringService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards() {
        return ResponseEntity.ok(cardService.findAll());//вернёт HTTP 200 OK + данные
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) {
//        return cardService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @GetMapping("/{id}")//@GetMapping("/{id}") — обработка GET запроса например /api/cards/4
    public ResponseEntity<?> getCardById(@PathVariable("id") Long id) {//@PathVariable("id") — говорит Spring: "получи id из URL-пути".
        //               явно указываем имя параметра ↑
        return cardService.findById(id)
                .map(ResponseEntity::ok)//Если найдена карточка: вернёт HTTP 200 OK + данные
                .orElse(ResponseEntity.notFound().build());//Если не найдена: вернёт HTTP 404 Not Found
    }

    @PostMapping//обработка POST запроса /api/cards
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {//@RequestBody — говорит: "получи тело запроса в виде объекта CardDto"
        return cardService.save(cardDto)
                .map(ResponseEntity::ok)//Если успешно: вернёт HTTP 200 OK и сохранённую карточку.
                .orElse(ResponseEntity.badRequest().build()); //Если ошибка: вернёт HTTP 400 Bad Request.
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CardDto> updateCard(@PathVariable Long id, @RequestBody CardDto cardDto) {
//        cardDto.setId(id);
//        return cardService.update(cardDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PutMapping("/{id}") //@PutMapping("/{id}") — обработка PUT запроса /api/cards/4
    public ResponseEntity<CardDto> updateCard(@PathVariable("id") Long id, @RequestBody CardDto cardDto) {
        //@PathVariable("id") — ID из URL
        //@RequestBody — новые данные карточки
        // Устанавливаем id из URL в объект CardDto: перезаписываем id из пути, чтобы избежать ошибок!
        cardDto.setId(id);
        System.out.println(id);
        System.out.println(cardDto.getId());
        System.out.println(cardDto.getCardNumber());
        // Вызываем сервис для обновления
        return cardService.update(cardDto)
                .map(ResponseEntity::ok)//Если обновление удалось: вернёт HTTP 200 OK
                .orElse(ResponseEntity.notFound().build()); //Если нет такой карточки: вернёт HTTP 404 Not Found
    }

    @DeleteMapping("/{id}") //@DeleteMapping("/{id}") — обработка DELETE запроса /api/cards/4
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        return cardService.delete(id)
                ? ResponseEntity.noContent().build()//Если удаление прошло успешно: вернёт HTTP 204 No Content (успешно, но тело пустое).
                : ResponseEntity.notFound().build(); //Если карточки нет: вернёт HTTP 404 Not Found.
    }
}
