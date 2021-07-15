import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObra, Obra } from 'app/shared/model/obra.model';
import { ObraService } from './obra.service';
import { ObraComponent } from './obra.component';
import { ObraDetailComponent } from './obra-detail.component';
import { ObraUpdateComponent } from './obra-update.component';

@Injectable({ providedIn: 'root' })
export class ObraResolve implements Resolve<IObra> {
  constructor(private service: ObraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObra> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((obra: HttpResponse<Obra>) => {
          if (obra.body) {
            return of(obra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Obra());
  }
}

export const obraRoute: Routes = [
  {
    path: '',
    component: ObraComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.obra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObraDetailComponent,
    resolve: {
      obra: ObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.obra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObraUpdateComponent,
    resolve: {
      obra: ObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.obra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObraUpdateComponent,
    resolve: {
      obra: ObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.obra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
