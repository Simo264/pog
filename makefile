TARGET = Main.class

SRC_DIR = src/
SRC_FILES = $(SRC_DIR)*.java

OUT_DIR = out/production/pog/

$(TARGET): $(SRC_FILES)
	javac $(SRC_FILES) -d $(OUT_DIR)

clean:
	rm -rf $(OUT_DIR)