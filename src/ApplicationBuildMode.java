/**
 * Rappresenta il metodo di compilazione ed esecuzione del programma:
 * questo perchè se compilato da CLI o IDE il percorso per la cartella
 * configs/ cambia.
 */
public class ApplicationBuildMode
{
  public static enum EBuildModes { IDE, CLI };
  public final static EBuildModes BUILD_MODE = EBuildModes.CLI;
}
