import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { LANGUAGES } from 'app/config/language.constants';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.scss'],
})
export class FooterComponent {
  languages = LANGUAGES;

  constructor(router: Router, private sessionStorageService: SessionStorageService, private translateService: TranslateService) {}

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }
}
