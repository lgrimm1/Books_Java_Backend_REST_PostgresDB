package lgrimm1.Books.Languages;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class LanguageEntityTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private void validateBean(Object bean) throws AssertionError {
		Optional<ConstraintViolation<Object>> violation = validator.validate(bean).stream().findFirst();
		if (violation.isPresent()) {
			throw new ValidationException(violation.get().getMessage());
		}
	}

	@Test
	void negativeId() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new LanguageEntity(-1, "name", "remarks")));
		Assertions.assertEquals("ID must be minimum 0.", e.getMessage());
	}

	@Test
	void minimumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(0, "name", "remarks")));
	}

	@Test
	void maximumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(Long.MAX_VALUE, "name", "remarks")));
	}

	@Test
	void nullName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new LanguageEntity(1, null, "remarks")));
		Assertions.assertEquals("Language name must exist.", e.getMessage());
	}

	@Test
	void emptyName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new LanguageEntity(1, "", "remarks")));
		Assertions.assertEquals("Language name must exist.", e.getMessage());
	}

	@Test
	void blankName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new LanguageEntity(1, " ", "remarks")));
		Assertions.assertEquals("Language name must exist.", e.getMessage());
	}

	@Test
	void existingName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(1, "name", "remarks")));
	}

	@Test
	void nullRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(1, "name", null)));
	}

	@Test
	void emptyRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(1, "name", "")));
	}

	@Test
	void blankRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(1, "name", " ")));
	}

	@Test
	void existingRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new LanguageEntity(1, "name", "remarks")));
	}
}