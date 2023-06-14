import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  cancionesRap(): void {
    const genre = 'RAP';
    this.router.navigate(['/song', genre]);
  }
  cancionesPop(): void {
    const genre = 'POP';
    this.router.navigate(['/song', genre]);
  }
  cancionesElectro(): void {
    const genre = 'TECHNO';
    this.router.navigate(['/song', genre]);
  }

  cancionesLofi(): void {
    const genre = 'LOFI';
    this.router.navigate(['/song', genre]);
  }
}
