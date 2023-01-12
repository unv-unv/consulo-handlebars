package com.dmarcotte.handlebars.pages;

import com.dmarcotte.handlebars.HbBundle;
import com.dmarcotte.handlebars.HbLanguage;
import com.dmarcotte.handlebars.config.HbConfig;
import consulo.annotation.component.ExtensionImpl;
import consulo.configurable.ApplicationConfigurable;
import consulo.configurable.ConfigurationException;
import consulo.configurable.SearchableConfigurable;
import consulo.language.Language;
import consulo.language.template.TemplateDataLanguageMappings;
import consulo.ui.ex.awt.ListCellRendererWrapper;
import consulo.ui.ex.awtUnsafe.TargetAWT;
import consulo.virtualFileSystem.fileType.FileType;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ExtensionImpl
public class HbConfigurationPage implements SearchableConfigurable, ApplicationConfigurable {
  private JCheckBox myAutoGenerateClosingTagCheckBox;
  private JPanel myWholePanel;
  private JCheckBox myFormattingCheckBox;
  private JComboBox myCommenterLanguage;

  @Nonnull
  @Override
  public String getId() {
    return "editor.hb";
  }

  @Nullable
  @Override
  public String getParentId() {
    return "editor";
  }

  @Nls
  @Override
  public String getDisplayName() {
    return HbBundle.message("hb.pages.options.title");
  }

  @Override
  public JComponent createComponent() {
    return myWholePanel;
  }

  @Override
  public boolean isModified() {
    return myAutoGenerateClosingTagCheckBox.isSelected() != HbConfig.isAutoGenerateCloseTagEnabled()
      || myFormattingCheckBox.isSelected() != HbConfig.isFormattingEnabled()
      || !((Language)myCommenterLanguage.getSelectedItem()).getID().equals(HbConfig.getCommenterLanguage().getID());
  }

  @Override
  public void apply() throws ConfigurationException {
    HbConfig.setAutoGenerateCloseTagEnabled(myAutoGenerateClosingTagCheckBox.isSelected());
    HbConfig.setFormattingEnabled(myFormattingCheckBox.isSelected());
    HbConfig.setCommenterLanguage((Language)myCommenterLanguage.getSelectedItem());
  }

  @Override
  public void reset() {
    myAutoGenerateClosingTagCheckBox.setSelected(HbConfig.isAutoGenerateCloseTagEnabled());
    myFormattingCheckBox.setSelected(HbConfig.isFormattingEnabled());
    resetCommentLanguageCombo(HbConfig.getCommenterLanguage());
  }

  private void resetCommentLanguageCombo(Language commentLanguage) {
    final DefaultComboBoxModel model = (DefaultComboBoxModel)myCommenterLanguage.getModel();
    final List<Language> languages = TemplateDataLanguageMappings.getTemplateableLanguages();

    // add using the native Handlebars commenter as an option
    languages.add(HbLanguage.INSTANCE);

    Collections.sort(languages, new Comparator<Language>() {
      @Override
      public int compare(final Language o1, final Language o2) {
        return o1.getID().compareTo(o2.getID());
      }
    });
    for (Language language : languages) {
      model.addElement(language);
    }

    // When com.intellij.openapi.fileTypes.impl.FileTypePatternDialog#FileTypePatternDialog updates to fix this warning, we make the same update here

    myCommenterLanguage.setRenderer(new ListCellRendererWrapper() {
      @Override
      public void customize(JList list, Object value, int index, boolean selected, boolean hasFocus) {
        setText(value == null ? "" : ((Language)value).getDisplayName());
        if (value != null) {
          final FileType type = ((Language)value).getAssociatedFileType();
          if (type != null) {
            setIcon(TargetAWT.to(type.getIcon()));
          }
        }
      }
    });
    myCommenterLanguage.setSelectedItem(commentLanguage);
  }
}
